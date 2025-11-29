"""
Vista que obtiene las últimas lecturas del MS Histórico
y predice la producción del siguiente día.
"""

"""
Usa los últimos 5 registros del usuario autenticado para predecir la producción.
Devuelve el valor predicho y los días usados.
"""
import requests
import numpy as np
from datetime import datetime, timedelta
from django.shortcuts import get_object_or_404
from rest_framework import status, permissions, viewsets
from rest_framework.decorators import action
from rest_framework.response import Response
from tensorflow.keras.models import load_model

from .models import PrediccionTomate
from .serializers import PrediccionTomateSerializer


# ------------------------------
# Cargar el modelo IA una sola vez
# ------------------------------
MODEL_PATH = "model/prediccion_tomates_5dias"
model = load_model(MODEL_PATH)

# Microservicio del histórico
HISTORICO_MS_URL = "http://127.0.0.1:6000/api/v1/predicciones/"


class PrediccionTomateViewSet(viewsets.ViewSet):
    """
    1. Obtiene las últimas 5 lecturas del MS-Histórico
    2. Ejecuta el modelo .keras
    3. Guarda la predicción en BD
    4. Devuelve respuesta al usuario
    """
    permission_classes = [permissions.IsAuthenticated]

    @action(detail=False, methods=['post'], url_path='predecir')
    def predecir(self, request):
        user_id = request.user.id
        token = request.auth  # JWT ya validado
        headers = {"Authorization": f"Bearer {token}"}

        # -------------------------------------------
        # 1. Obtener datos del microservicio HISTÓRICO
        #-------------------------------------------
        try:
            response = requests.get(HISTORICO_MS_URL, headers=headers, timeout=10)
            response.raise_for_status()
        except requests.RequestException:
            return Response(
                {"error": "No se pudo conectar al microservicio histórico."},
                status=status.HTTP_502_BAD_GATEWAY
            )

        historicos = response.json()

        if not historicos or len(historicos) < 5:
            return Response(
                {"error": f"Se requieren al menos 5 registros. Solo hay {len(historicos)}."},
                status=status.HTTP_400_BAD_REQUEST
            )

        # -------------------------------------------
        # 2. Ordenar y tomar últimos 5
        #-------------------------------------------
        lecturas = sorted(historicos, key=lambda x: x["created_at"])[-5:]

        # -------------------------------------------
        # 3. Preparar datos para modelo IA
        #-------------------------------------------
        try:
            datos = np.array([
                [
                    float(l["Humedad_Media"]),
                    float(l["Temp_Media"]),
                    float(l["Longitud_Tallo"]),
                    float(l["Diametro_Tallo"]),
                ]
                for l in lecturas
            ])
        except KeyError as e:
            return Response(
                {"error": f"Falta campo en los datos del histórico: {str(e)}"},
                status=status.HTTP_500_INTERNAL_SERVER_ERROR
            )

        entrada = datos.reshape(1, 5, 4)

        # -------------------------------------------
        # 4. Hacer predicción
        #-------------------------------------------
        pred = model.predict(entrada)
        resultado = float(pred[0][0])

        # -------------------------------------------
        # 5. Calcular día predicho
        #-------------------------------------------
        ultimo_dia = datetime.fromisoformat(lecturas[-1]["created_at"].replace("Z", ""))
        dia_siguiente = ultimo_dia + timedelta(days=1)

        # -------------------------------------------
        # 6. Guardar predicción en BD
        #-------------------------------------------
        prediccion = PrediccionTomate.objects.create(
            user_id=user_id,
            dia_predicho=dia_siguiente.date(),
            produccion_predicha_kg=resultado,
            dias_usados=[l["created_at"] for l in lecturas]
        )

        # -------------------------------------------
        # 7. Respuesta al usuario
        #-------------------------------------------
        return Response({
            "usuario": str(user_id),
            "dia_predicho": dia_siguiente.strftime("%Y-%m-%d"),
            "produccion_predicha_kg": round(resultado, 2),
        }, status=status.HTTP_200_OK)


# ============================================================
# VIEWSET PARA LISTAR PREDICCIONES DEL USUARIO
# ============================================================

class ListarPrediccionesViewSet(viewsets.ViewSet):
    permission_classes = [permissions.IsAuthenticated]

    def list(self, request):
        user_id = request.user.id

        predicciones = PrediccionTomate.objects.filter(
            user_id=user_id
        ).order_by('-creado_en')

        serializer = PrediccionTomateSerializer(predicciones, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)
