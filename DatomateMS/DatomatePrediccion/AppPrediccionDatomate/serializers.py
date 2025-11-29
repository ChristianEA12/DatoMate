
from rest_framework import serializers
from AppPrediccionDatomate.models import Historico

class HistoricoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Historico
        fields = [
            'id',
            'Temp_Media',
            'Humedad_Media',
            'Longitud_Tallo',
            'Diametro_Tallo',
            'created_at',
        ]
        read_only_fields = ['id', 'created_at']
