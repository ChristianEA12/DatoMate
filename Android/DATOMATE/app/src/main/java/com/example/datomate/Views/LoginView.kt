package com.example.datomate.Views

import com.example.datomate.Components.AppLogoBar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.datomate.Components.ForgotPasswordLink
import com.example.datomate.Components.PrimaryButton
import com.example.datomate.Components.PrimaryTextField
import com.example.datomate.Components.SecondaryOutlinedButton
import com.example.datomate.Components.Space
import com.example.datomate.Components.Title

@Composable
fun LoginView(
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onDataClick: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppLogoBar(180, Alignment.Center)

            Card(
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomEnd = 30.dp, bottomStart = 30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 70.dp, start = 20.dp, end = 20.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                ContentLoginView(onRegisterClick, onForgotPasswordClick, onDataClick)
            }
        }
    }
}

@Composable
fun ContentLoginView(
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onDataClick: () -> Unit
){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 40.dp)
    ) {
        Title("Iniciar sesión")

        // Campos de texto
        PrimaryTextField(
            label = "Correo electrónico",
            value = email,
            onValueChange = { email = it }
        )
        Space(16)
        PrimaryTextField(
            label = "Contraseña",
            value = password,
            onValueChange = { password = it }
        )

        // Olvidaste Contraseña
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ForgotPasswordLink(onClick = onForgotPasswordClick)
        }
        Space(24)

        // Botón de Iniciar Sesión
        PrimaryButton(
            text = "Iniciar sesión",
            onClick =  /* {Lógica de Login} */ onDataClick
        )
        Space(24)
        // Separador "O"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )
            Text(
                " O ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )
        }
        Space(24)

        // Botón de Regístrate
        SecondaryOutlinedButton(
            text = "Regístrate",
            onClick = onRegisterClick
        )
    }
}