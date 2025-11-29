from rest_framework.routers import DefaultRouter

from AppPrediccionDatomate.views import HistoricoViewSet
from django.urls import path, include


router = DefaultRouter()
router.register(r'predicciones', HistoricoViewSet, basename='predicciones')

urlpatterns = [
    path('', include(router.urls)),
]