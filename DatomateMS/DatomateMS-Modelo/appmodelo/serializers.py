from rest_framework import serializers
from appmodelo.models import PrediccionTomate


class PrediccionTomateSerializer(serializers.ModelSerializer):
    class Meta:
        model = PrediccionTomate
        fields = [
            'id',
            'user_id',
            'dia_predicho',
            'produccion_predicha_kg',
            'dias_usados',
            'creado_en'
        ]
