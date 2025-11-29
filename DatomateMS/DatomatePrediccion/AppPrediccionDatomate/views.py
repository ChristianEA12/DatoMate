from rest_framework import viewsets, permissions, status
from rest_framework.response import Response
from rest_framework.decorators import action

from AppPrediccionDatomate import serializers
from AppPrediccionDatomate.models import Historico
from AppPrediccionDatomate.permissions import IsOwner


class HistoricoViewSet(viewsets.ModelViewSet):
    """
    API endpoint que gestiona los registros históricos de un usuario (autenticado por JWT)
    """
    serializer_class = serializers.HistoricoSerializer
    permission_classes = [permissions.IsAuthenticated, IsOwner]

    def get_queryset(self):
        user_id = self.request.user.id
        # Devuelve los registros históricos que pertenecen al usuario
        return Historico.objects.filter(user_id=user_id)

    def perform_create(self, serializer):
        user_id = self.request.user.id
        serializer.save(user_id=user_id)

    # Endpoint opcional: filtrar por temperatura media
    @action(detail=False, methods=['get'], url_path='by-temp')
    def by_temp(self, request):
        """
        Endpoint especial para recuperar información de registros según Temp_Media
        Ejemplo: GET /api/v1/historico/by-temp/?value=25
        """
        temp_value = request.query_params.get('value', None)
        if temp_value is None:
            return Response({'error': 'Debe proporcionar el parámetro ?value='},
                            status=status.HTTP_400_BAD_REQUEST)
        try:
            historicos = Historico.objects.filter(Temp_Media=float(temp_value))
            serializer = self.get_serializer(historicos, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
        except ValueError:
            return Response({'error': 'El valor de temperatura debe ser numérico'},
                            status=status.HTTP_400_BAD_REQUEST)
