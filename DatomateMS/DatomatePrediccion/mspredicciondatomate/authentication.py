from types import SimpleNamespace
from rest_framework_simplejwt.authentication import JWTAuthentication


class CustomJWTAuthentication(JWTAuthentication):
    """
    clase para autenticar a los usuarios sin consultar BD
    """
    def get_user(self, validated_token):
        #Extraer el user_id
        user_id = validated_token.get('sub')
        if not user_id:
            return None

        # Crear un objeto simple de usuario
        user = SimpleNamespace(id=user_id)
        user.is_authenticated = True
        return user

