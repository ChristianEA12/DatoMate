import requests
from django.contrib import messages

from django.shortcuts import render, redirect
from django.views import View


# Vista para la página de Inicio
def inicio_view(request):
    # Nota: Aquí es donde normalmente harías la llamada a tu API externa
    # para obtener los datos de Humedad, Temperatura, Producción y los registros.
    # Por ahora, usaremos datos de ejemplo para renderizar la plantilla.

    context = {
        # 'current_page' se usa en la plantilla base para resaltar el menú activo
        'current_page': 'inicio',
        'humedad': '65%',
        'temperatura': '28 °C',
        'produccion': '600 Kg',
        # Datos de ejemplo para la tabla (Últimos registros)
        'ultimos_registros': [
            {'fecha': '30/11/25', 'temp': '20°C', 'hum': '61%'},
            {'fecha': '29/11/25', 'temp': '10°C', 'hum': '73%'},
            {'fecha': '28/11/25', 'temp': '20°C', 'hum': '64%'},
            {'fecha': '27/11/25', 'temp': '18°C', 'hum': '84%'},
            {'fecha': '26/11/25', 'temp': '14°C', 'hum': '77%'},
            {'fecha': '25/11/25', 'temp': '17°C', 'hum': '74%'},
        ]
    }

    return render(request, 'usuarios/inicio.html', context)


def captura_view(request):
    # La lógica para procesar el formulario POST (envío de datos a la API)
    if request.method == 'POST':
        # Aquí iría el código para recoger los datos y enviarlos a la API
        # Por ahora, solo vamos a simular un éxito y redirigir:

        # 1. Recoger los datos del formulario (ejemplo)
        humedad = request.POST.get('humedad')
        temperatura = request.POST.get('temperatura')
        longitud_tallo = request.POST.get('longitud_tallo')
        diametro_tallo = request.POST.get('diametro_tallo')
        notas = request.POST.get('notas')

        # 2. Lógica de comunicación con la API externa (PENDIENTE)
        # requests.post(API_URL, data={'...'})

        # 3. Mostrar un mensaje de éxito (usaremos el sistema de messages de Django)
        from django.contrib import messages
        messages.success(request, f'Datos capturados exitosamente: Humedad={humedad}, Temp={temperatura}')

        # 4. Redirigir a la misma página o a una de éxito
        return redirect('usuarios:captura')

    # La lógica para mostrar el formulario (método GET)
    context = {
        'current_page': 'captura',
    }
    return render(request, 'usuarios/captura.html', context)

def reportes_view(request):
    context = {
        'current_page': 'reportes',
    }
    return render(request, 'usuarios/reportes.html', context)

def predicciones_view(request):
    context = {
        'current_page': 'predicciones',
    }
    return render(request, 'usuarios/predicciones.html', context)


def alertas_view(request):
    # Datos de ejemplo para las alertas
    alertas_activas = [
        {'tipo': 'Temperatura alta en el lote', 'tiempo': 'Hace 2 horas', 'color': 'warning'},
        {'tipo': 'Posible plaga: Araña roja', 'confianza': '92% de confianza', 'tiempo': 'Hace 5 minutos',
         'color': 'danger', 'leido': False},
        {'tipo': 'Posible plaga: Plaga del riego', 'confianza': '54% de confianza', 'tiempo': 'Hace 5 minutos',
         'color': 'warning', 'leido': True},
    ]

    context = {
        'current_page': 'alertas',
        'alertas_activas': alertas_activas,
    }
    return render(request, 'usuarios/alertas.html', context)


def perfil_view(request):
    if request.method == 'POST':
        # Lógica para procesar la actualización de datos
        pais = request.POST.get('pais')
        username = request.POST.get('username')
        descripcion = request.POST.get('descripcion')

        # 1. Lógica de comunicación con la API externa para actualizar (PENDIENTE)
        # requests.post(API_URL_UPDATE, data={'...'})

        messages.success(request, f'Perfil actualizado exitosamente para el usuario: {username}')
        return redirect('usuarios:perfil')

    # La lógica para mostrar el formulario (método GET)
    context = {
        'current_page': 'perfil',
        # Datos de ejemplo o datos obtenidos de la API (simulación)
        'datos_perfil': {
            'nombre_actual': 'Jhonny Castillo',
            'pais_actual': 'México',
            'email': 'jhonny.c@datomate.com',
            'descripcion_actual': 'Experto en monitoreo y predicción de cultivos de tomate.',
        }
    }
    return render(request, 'usuarios/perfil.html', context)

def cerrar_sesion_view(request):
    # Por ahora, simplemente redirige a inicio o a la página de login (si la tuvieras)
    return render(request, 'usuarios/placeholder.html', {'current_page': '', 'titulo': 'Cerrar Sesión'})


#ViewS de Usuario enfocado a su cuenta -------------------------------
from django.shortcuts import render, redirect
from django.views import View
from django.contrib import messages
import requests


class activarCuenta(View):
    template_name = "inicioUsuarios/active_account.html"
    # URL de tu microservicio MS-Usuarios
    api_url = "http://127.0.0.1:5000/accounts/activar/"

    def get(self, request):
        return render(request, self.template_name, {})

    def post(self, request):
        # 1. Recibir la información del formulario
        email = request.POST.get("email")
        otp = request.POST.get("otp")

        # 2. Preparar el JSON
        payload = {
            "email": email,
            "otp": otp,
        }
        print("Enviando payload de activación:", payload)

        try:
            # 3. Enviar al microservicio
            response = requests.post(self.api_url, json=payload)

            try:
                data = response.json()
            except ValueError:
                data = {"detail": "Respuesta inesperada del servidor."}

            # 4. Verificar respuesta (200 OK suele ser para activación exitosa)
            if response.status_code == 200:
                # Éxito
                messages.success(request, "¡Cuenta activada correctamente! Ya puedes iniciar sesión.")
                print("Cuenta activada.")

                # Redireccionar al Login
                return redirect("usuarios:login")

            else:
                # Fallo (OTP incorrecto, expirado, usuario no encontrado, etc.)
                mensaje_error = data.get("detail") or data.get("error") or "No se pudo activar la cuenta."
                messages.error(request, mensaje_error)
                print(f"Error activando cuenta: {mensaje_error}")

                # Renderizamos la misma página para que intente de nuevo.
                # Pasamos el 'email' en el contexto para que no tenga que escribirlo otra vez
                return render(request, self.template_name, {"email": email})

        except requests.exceptions.RequestException as e:
            # 5. Error de conexión
            err_msg = "Error al conectar con el servicio de Activación"
            messages.error(request, err_msg)
            print("Error de Conexión:", e)
            return render(request, self.template_name, {"email": email})


class forgotCuenta(View):
    template_name = "inicioUsuarios/forgot_password.html"
    api_url = "http://127.0.0.1:5000/accounts/forgot/"

    def get(self, request):
        return render(request, self.template_name, {})

    def post(self, request):
        telefono_celular = request.POST.get("telefono_celular")
        email = request.POST.get("email")  # Opcional, por si usas ambos

        if not telefono_celular and not email:
            messages.error(request, "Por favor ingresa un teléfono o correo.")
            return render(request, self.template_name, {})


        payload = {}
        if telefono_celular:
            payload["telefono_celular"] = telefono_celular
        if email:
            payload["email"] = email

        print("Solicitando recuperación para:", payload)

        try:
            response = requests.post(self.api_url, json=payload)

            try:
                data = response.json()
            except ValueError:
                data = {"detail": "Error inesperado del servidor."}

            if response.status_code == 200:
                messages.success(request, "Se ha enviado un código OTP a tu correo/teléfono.")

                # Guardamos el identificador en la sesión para el siguiente paso
                # Así la vista de 'reset' sabe a quién cambiarle la contraseña
                if telefono_celular:
                    request.session['reset_telefono'] = telefono_celular
                if email:
                    request.session['reset_email'] = email

                # Redirigir a la vista de "Reset Password" (donde ponen el OTP y la nueva clave)
                # Asegúrate de tener esta URL definida en tus urls.py, por ejemplo 'usuarios:reset'
                return redirect("usuarios:reset")

            else:
                # Error (Usuario no encontrado, etc.)
                mensaje_error = data.get("detail") or data.get("error") or "No se pudo procesar la solicitud."
                messages.error(request, mensaje_error)
                return render(request, self.template_name, {})

        except requests.exceptions.RequestException as e:
            err_msg = "Error al conectar con el servicio de Recuperación"
            messages.error(request, err_msg)
            print("Error de Conexión:", e)
            return render(request, self.template_name, {})

class loginCuenta(View):
    template_name = "inicioUsuarios/login.html"
    api_url = "http://127.0.0.1:5000/accounts/login/"

    def get(self, request):
        return render(request, self.template_name, {})

    def post(self, request):
        # Recibir la informacion del front
        identificador = request.POST.get("identificador")
        password = request.POST.get("password")

        payload = {
            "identificador": identificador,
            "password": password,
        }
        print(payload)
        try:
            response = requests.post(self.api_url, json=payload)
            data = response.json()
            # verificar la respuesta
            if response.status_code != 200:
                messages.error(request, "Credenciales incorrectas...")
                return render(request, self.template_name, {"Error": data.get(
                    "detail", "Credenciales incorrectas")
                })

            # Guardar los tokens
            request.session["access_token"] = data.get("access_token")  # **********
            request.session["refresh_token"] = data.get("refresh_token")
            request.session["user"] = data.get("user")
            print("...")
            # Esta es la corrección
            messages.success(request, data.get("mensaje", "Bienvenido a Datomate"))
            print("Usuario Encontrado y redireccionando...")
            return redirect("/inicioAfterLogin/")

        except requests.exceptions.RequestException as e:
            messages.error(request, "Error al conectar con el servicion de Autentificacion")
            print("Error de Conexion", e)
            return render(request, self.template_name,
                          {"Error": "Error al conectar con el servicion de Autentificacion"})


class registroCuenta(View):
    template_name = "inicioUsuarios/registro.html"
    # Asegúrate que esta URL sea la correcta de tu microservicio MS-Usuarios
    api_url = "http://127.0.0.1:5000/accounts/registro/"

    def get(self, request):
        return render(request, self.template_name, {})

    def post(self, request):
        email = request.POST.get("email")
        telefono_celular = request.POST.get("telefono_celular")
        nombre_usuario = request.POST.get("nombre_usuario")
        password = request.POST.get("password")
        password2 = request.POST.get("password2")

        payload = {
            "email": email,
            "telefono_celular": telefono_celular,
            "nombre_usuario": nombre_usuario,
            "password": password,
            "password2": password2
        }
        print("Enviando payload:", payload)

        try:
            response = requests.post(self.api_url, json=payload)

            # Intentar decodificar la respuesta JSON, incluso si hay error
            try:
                data = response.json()
            except ValueError:
                data = {"detail": "Error inesperado en la respuesta del servidor."}

            if response.status_code not in [200, 201]:
                mensaje_error = data.get("detail") or data.get("error") or str(data)

                messages.error(request, f"Error al registrar: {mensaje_error}")
                print(f"Error API ({response.status_code}): {mensaje_error}")

                # Renderizamos de nuevo el registro con el error
                return render(request, self.template_name, {"Error": mensaje_error})

            # Éxito
            print("Usuario registrado exitosamente.")
            messages.success(request, "Cuenta creada con éxito. Por favor activala.")

            # Redireccionar al Login
            return redirect("usuarios:activar")

        except requests.exceptions.RequestException as e:
            # Error de conexión (Microservicio apagado, etc.)
            err_msg = "Error al conectar con el servicio de Registro"
            messages.error(request, err_msg)
            print("Error de Conexión:", e)
            return render(request, self.template_name, {"Error": err_msg})


class resetCuenta(View):
    template_name = "inicioUsuarios/reset_password.html"
    # Asegúrate que esta URL coincida con tu MS-Usuarios
    api_url = "http://127.0.0.1:5000/accounts/reset/"

    def get(self, request):
        return render(request, self.template_name, {})

    def post(self, request):
        # 1. Recuperar el identificador del usuario
        # Intentamos obtenerlo del formulario (si lo pusiste en un input hidden)
        telefono_celular = request.POST.get("telefono_celular")
        email = request.POST.get("email")

        # Si no viene en el formulario, lo sacamos de la sesión (del paso anterior 'forgot')
        if not telefono_celular:
            telefono_celular = request.session.get('reset_telefono')
        if not email:
            email = request.session.get('reset_email')

        # Si no tenemos ni uno ni otro, hay un error de flujo
        if not telefono_celular and not email:
            messages.error(request,
                           "Error de sesión: No se identificó al usuario. Intenta el proceso de recuperación nuevamente.")
            return redirect("usuarios:forgot")  # Redirige al inicio del proceso

        # 2. Obtener el resto de datos
        otp = request.POST.get("otp")
        new_password = request.POST.get("new_password")
        new_password2 = request.POST.get("new_password2")

        # 3. Armar el payload
        payload = {
            "otp": otp,
            "new_password": new_password,
            "new_password2": new_password2
        }
        # Añadir el identificador que tengamos disponible
        if telefono_celular:
            payload["telefono_celular"] = telefono_celular
        if email:
            payload["email"] = email

        print("Enviando reset payload:", payload)

        try:
            # 4. Enviar a la API
            response = requests.post(self.api_url, json=payload)

            try:
                data = response.json()
            except ValueError:
                data = {"detail": "Error inesperado del servidor."}

            # 5. Verificar éxito (200 OK)
            if response.status_code == 200:
                messages.success(request, "¡Contraseña restablecida con éxito! Por favor inicia sesión.")

                # Limpiar la sesión (ya no necesitamos guardar el teléfono/mail)
                if 'reset_telefono' in request.session:
                    del request.session['reset_telefono']
                if 'reset_email' in request.session:
                    del request.session['reset_email']

                # Redirigir al Login
                return redirect("usuarios:login")

            else:
                # Error (OTP incorrecto, contraseñas no coinciden, expirado)
                mensaje_error = data.get("detail") or data.get("error") or "No se pudo cambiar la contraseña."

                # Manejo especial para errores de validación de campos (diccionarios)
                if isinstance(data, dict):
                    # Si el API devuelve {"new_password": ["Las contraseñas no coinciden"]}
                    for key, value in data.items():
                        if isinstance(value, list):
                            mensaje_error = f"{key}: {value[0]}"
                            break

                messages.error(request, mensaje_error)
                print(f"Error reset password: {mensaje_error}")

                # Regresamos el OTP para que no tenga que volver a escribirlo si solo falló el password
                return render(request, self.template_name, {"otp": otp})

        except requests.exceptions.RequestException as e:
            err_msg = "Error al conectar con el servicio de Restablecimiento"
            messages.error(request, err_msg)
            print("Error de Conexión:", e)
            return render(request, self.template_name, {"otp": otp})

class inicioCuenta(View):
    template_name = "inicioUsuarios/inicioCuenta.html"

    def get(self, request):
        return render(request, self.template_name, {})

    def post(self, request):
        return render(request, self.template_name, {})