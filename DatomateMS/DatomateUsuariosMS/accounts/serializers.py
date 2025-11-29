from django.contrib.auth.hashers import make_password
from rest_framework import serializers
from django.utils import timezone
from rest_framework.validators import UniqueValidator

from accounts.models import Usuario

# Para LOGIN
from django.contrib.auth import authenticate
from rest_framework_simplejwt.tokens import RefreshToken

from accounts.utilities.utils import enviar_otp_mail, enviar_otp_sms, enviar_otp_mail_forgot


class RegistroUsuarioSerializer(serializers.ModelSerializer):

    password2 = serializers.CharField(write_only=True, style={'input_type': 'password'})

    email = serializers.EmailField(
        validators=[
            UniqueValidator(
                queryset=Usuario.objects.all(),
                message="El correo electrónico ya está registrado. Intenta con otro."
            )
        ]
    )

    telefono_celular = serializers.CharField(
        validators=[
            UniqueValidator(
                queryset=Usuario.objects.all(),
                message="El número de teléfono ya está registrado. Intente con otro"
            )
        ]
    )

    class Meta:
        model = Usuario
        fields = ('nombre_usuario', 'telefono_celular', 'email', 'password', 'password2')
        extra_kwargs = {
            'password': {'write_only': True}
        }

    def validate(self, validated_data):
        # Verificar las conttraseñas
        if validated_data['password'] != validated_data['password2']:
            raise serializers.ValidationError({'password': 'Las contraseñas no coinciden'})
        return validated_data

    def create(self, validated_data):
        # Extraer el campo password2
        validated_data.pop('password2')

        # Generar el OTP
        otp = Usuario.generar_otp()

        mi_usuario = Usuario.objects.create_user( # Usuario.objects.create_user(  # Para que pase por las validaciones de DRF
            email=validated_data['email'],
            password=validated_data['password'],
            nombre_usuario=validated_data['nombre_usuario'],
            telefono_celular=validated_data['telefono_celular'],
            is_active=False,
            otp_codigo = otp,
            otp_creado_en = timezone.now()
        )

        return mi_usuario

class LoginUsuarioSerializer(serializers.Serializer):
    # Crear identificador para email o telefono_celular
    identificador = serializers.CharField(write_only=True)
    password = serializers.CharField(write_only=True, style={'input_type': 'password'})

    access_token = serializers.CharField(read_only=True)
    refresh_token = serializers.CharField(read_only=True)
    user = serializers.SerializerMethodField(read_only=True)

    def validate(self, attrs):
        identificador = attrs.get('identificador')
        password = attrs.get('password')
        user = None
        # INTENTAR AUTENTICAR
        user = authenticate(username=identificador, password=password)
        if user is None:
            # Intentar buscar al usuario por correo electronico
            try:
                usuario_obj = Usuario.objects.get(email=identificador)
                user = authenticate(username=usuario_obj.telefono_celular, password=password)
                print("Usuario encontrado", usuario_obj.telefono_celular)
            except Usuario.DoesNotExist:
                user = None
                print(f"Usuario no encontrado: {identificador}.")
        if user is None:
            raise serializers.ValidationError({'error': 'Credenciales no validas'})
        if not user.is_active:
            raise serializers.ValidationError({'error': 'No está activada la cuenta'})
        #Generamos los JWT
        refresh_token = RefreshToken.for_user(user)
        access_token = refresh_token.access_token
        return {
            'refresh_token': str(refresh_token),
            'access_token': str(access_token),
            'user': {
                'id': user.pk,
                'nombre_usuario': getattr(user, 'nombre_usuario', ''),
                'email': user.email,
                'telefono_celular': user.telefono_celular,
            },

        }
    def get_user(self, obj):
        return obj.get('user')

class ForgotPasswordSerializer(serializers.Serializer):
    email = serializers.EmailField(required=False)
    telefono_celular = serializers.CharField(required=False)

    def validate(self, attrs):
        email = attrs.get('email')
        telefono = attrs.get('telefono_celular')

        if not email and not telefono:
            raise serializers.ValidationError("Debes proporcionar email o telefono")

        try:
            if email:
                usuario = Usuario.objects.get(email=email)
            else:
                usuario = Usuario.objects.get(telefono_celular=telefono)
        except Usuario.DoesNotExist:
            raise serializers.ValidationError('Usuario no encontrado')

        #Generar Otp y guardarlo
        otp = Usuario.generar_otp()
        usuario.otp_codigo = otp
        usuario.otp_creado_en = timezone.now()
        usuario.save()

        # Aquí llamas a tu función enviar_otp_email(usuario) o enviar sms(usuario)
        print(f"Otp de recuperación: {otp}")
        try:
            if usuario.email and email:
                enviar_otp_mail_forgot(usuario)
            elif usuario.telefono_celular and telefono:
                enviar_otp_sms(usuario)
            else:
                raise serializers.ValidationError(
                    "No se encontró un medio de contacto válido para enviar el OTP"
                )
        except Exception as e:
            raise serializers.ValidationError(f"No se pudo enviar el OTP por correo electronico o telefono: {str(e)}")
        attrs["usuario"] = usuario
        return attrs



class ResetPasswordSerializer(serializers.Serializer):
    email = serializers.EmailField(required=False)
    telefono_celular = serializers.CharField(required=False)

    otp = serializers.CharField()
    new_password = serializers.CharField(write_only=True)
    new_password2 = serializers.CharField(write_only=True)

    def validate(self, attrs):
        email = attrs.get('email')
        telefono = attrs.get('telefono_celular')
        otp = attrs.get('otp')

        if not email and not telefono:
            raise serializers.ValidationError("Debes proporcionar email o telefono")

        try:
            if email:
                usuario = Usuario.objects.get(email=email)
            else:
                usuario = Usuario.objects.get(telefono_celular=telefono)
        except Usuario.DoesNotExist:
            raise serializers.ValidationError({'Usuario no encontrado'})

        #Validar otp
        if usuario.otp_codigo != otp:
            raise serializers.ValidationError("Codigo otp invalido")

        tiempo_expiracion = usuario.otp_creado_en + timezone.timedelta(minutes=5)
        if timezone.now() > tiempo_expiracion:
            raise serializers.ValidationError("El código otp ha expirado")

        #Validar contraseñas
        if attrs.get('new_password') != attrs.get('new_password2'):
            raise serializers.ValidationError('Las contraseñas no coinciden')

        attrs["usuario"] = usuario
        return attrs

    def save(self, **kwargs):
        usuario = self.validated_data['usuario']
        usuario.password = make_password(self.validated_data['new_password'])
        usuario.otp_codigo = None # invalidar otp
        usuario.save()
        return usuario

