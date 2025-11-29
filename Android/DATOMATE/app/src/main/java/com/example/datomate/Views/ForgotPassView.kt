package com.example.datomate.Views


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.datomate.Components.AccountNavigationLink
import com.example.datomate.Components.AppLogoBar
import com.example.datomate.Components.PrimaryButton
import com.example.datomate.Components.PrimaryTextField
import com.example.datomate.Components.RecoveryMethodSwitch
import com.example.datomate.Components.Space
import com.example.datomate.Components.Title

// Definición de las dos variantes
enum class RecoveryType { EMAIL, PHONE }

@Composable
fun ForgotPassView(
    type: RecoveryType,
    onSwitchMethodClick: () -> Unit,
    onLoginClick: () -> Unit
) {


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary // Asumiendo que el verde oscuro es primary o tertiary en tu Theme
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppLogoBar(180, Alignment.Center)

            Card(
                shape = RoundedCornerShape(30.dp), // Esquinas redondeadas en todas partes
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 70.dp, start = 20.dp, end = 20.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                ContenteForgotPassView(type, onSwitchMethodClick, onLoginClick)
            }
        }
    }
}

@Composable
fun ContenteForgotPassView(
    type: RecoveryType,
    onSwitchMethodClick: () -> Unit,
    onLoginClick: () -> Unit)
{
    // 1. Estados
    var recoveryValue by remember { mutableStateOf("") }

    // 2. Textos dinámicos basados en el 'type'
    val titleText = "¿Olvidaste tu contraseña?"
    val descriptionText = "Ingresa tu correo electrónico o tu número de teléfono y te enviaremos un código de verificación."

    val primaryLabel = if (type == RecoveryType.EMAIL) "Correo electrónico" else "Teléfono"
    val switchText = if (type == RecoveryType.EMAIL) "Recuperar mediante SMS" else "Recuperar mediante correo electrónico"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 40.dp)
    ) {
        // Título principal
        Title(titleText)

        // Subtítulo de recuperación
        Text(
            "Recupera el acceso",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Descripción
        Text(
            descriptionText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 24.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Campo de Texto (Correo o Teléfono)
        PrimaryTextField(
            label = primaryLabel,
            value = recoveryValue,
            onValueChange = { recoveryValue = it }
        )

        Space(16)

        // Enlace para cambiar el método
        RecoveryMethodSwitch(
            text = switchText,
            onClick = onSwitchMethodClick,
            modifier = Modifier.align(Alignment.Start) // Alineación a la izquierda
        )

        Space(32)

        // Botón de Enviar Código
        PrimaryButton(
            text = "Enviar código de verificación",
            onClick = { /* Lógica de envío */ }
        )

        Space(24)

        // Enlace a Iniciar Sesión (Componente reutilizado)
        AccountNavigationLink(
            prefixText = "¿Recordaste tu contraseña? ",
            linkText = "Inicia sesión",
            onLinkClick = onLoginClick
        )
    }
}