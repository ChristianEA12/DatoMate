# appmodelo/urls.py
from rest_framework.routers import DefaultRouter
from django.urls import path, include
from appmodelo.views import PrediccionTomateViewSet, ListarPrediccionesViewSet

router = DefaultRouter()
router.register(r'prediccion', PrediccionTomateViewSet, basename='prediccion')
router.register(r'predicciones', ListarPrediccionesViewSet, basename='listar-predicciones')

urlpatterns = router.urls
