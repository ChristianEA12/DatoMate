from django.urls import path

from usuarios import views

urlpatterns = [
    path('', views.inicioCuenta.as_view(), name='inicio'),  # (Ya la definimos)
    path('captura/', views.captura_view, name='captura'),
    path('reportes/', views.reportes_view, name='reportes'),
    path('predicciones/', views.predicciones_view, name='predicciones'),
    path('alertas/', views.alertas_view, name='alertas'),
    path('perfil/', views.perfil_view, name='perfil'),
    path('logout/', views.cerrar_sesion_view, name='cerrar_sesion'),  # Cerrar Sesión
    path('activar/', views.activarCuenta.as_view(), name='activar'),
    path('forgot/', views.forgotCuenta.as_view(), name='forgot'),
    path('login/', views.loginCuenta.as_view(), name='login'),
    path('registro/', views.registroCuenta.as_view(), name='registro'),
    path('reset/', views.resetCuenta.as_view(), name='reset'),
    path('inicioAfterLogin/', views.inicio_view, name='inicioAfterLogin'),


]