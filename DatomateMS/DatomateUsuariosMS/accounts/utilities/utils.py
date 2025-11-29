from django.core.mail import send_mail
from datomateusuarios import settings
import base64
import requests

def enviar_otp_mail(usr):
    asunto = "Activa tu cuenta Datomate"
    mensaje = f"""
    Gracias por registrarte en Datomate. Tu código activación es: {usr.otp_codigo}

    Este código es válido por 5 minjutos.

    Atte
    Equipo de soporte Datomate 🍅
    """
    # Utilizar el metodo send_mail
    send_mail(
        subject=asunto,  # Asunto
        message=mensaje,
        from_email=settings.DEFAULT_FROM_EMAIL,
        recipient_list=[usr.email],
        fail_silently=False,
    )

def enviar_otp_mail_forgot(usr):
    asunto = "Recupera tu cuenta Datomate"
    mensaje = f"""
    Tu código  de recuperación es: {usr.otp_codigo}
    
    Este código es válido por 5 minjutos.
    
    Atte
    Equipo de soporte Datomate 🍅
    """
    # Utilizar el metodo send_mail
    send_mail(
        subject=asunto,  # Asunto
        message=mensaje,
        from_email=settings.DEFAULT_FROM_EMAIL,
        recipient_list=[usr.email],
        fail_silently=False,
    )

def enviar_otp_sms(usr):
    # Credenciales codificadas en Base64
    credentials = f"{settings.VONAGE_API_KEY}:{settings.VONAGE_API_SECRET}"
    encoded = base64.b64encode(credentials.encode("utf-8")).decode("ascii")

    mensaje = f"Tu código para recuperar la contraseña Datomate es: {usr.otp_codigo}. Válido por 5 minutos."

    # Asegurar formato internacional del número (México = 52)
    numero = usr.telefono_celular
    if not numero.startswith("52"):
        numero = f"52{numero}"

    # Petición HTTP a la API de Vonage
    response = requests.post(
        "https://api.nexmo.com/v0.1/messages",
        headers={
            "Authorization": f"Basic {encoded}",
            "Content-Type": "application/json",
            "Accept": "application/json",
        },
        json={
            "to": {"type": "sms", "number": numero},
            "from": {"type": "sms", "number": settings.VONAGE_FROM},
            "message": {
                "content": {"type": "text", "text": mensaje}
            },
        },
    )

    data = response.json()
    if response.status_code == 202:
        print("SMS enviado correctamente:", data)
        return True
    else:
        print("Error al enviar SMS:", data)
        return False