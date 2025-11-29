import uuid
from django.db import models

# Clases principales datomate
class Historico(models.Model):
    # Definir un id del tipo uuid
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    user_id = models.UUIDField(db_index=True)

    # Campos que interesan
    Temp_Media = models.FloatField()
    Humedad_Media = models.FloatField()
    Longitud_Tallo = models.FloatField()
    Diametro_Tallo = models.FloatField()

    # Fecha de creación y actualización
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f"T: {self.Temp_Media}°C, H: {self.Humedad_Media}%, L: {self.Longitud_Tallo}cm, D: {self.Diametro_Tallo}cm"

    class Meta:
        ordering = ['created_at']
