package com.example.datomate.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.datomate.Components.AccountNavigationLink
import com.example.datomate.Components.AppLogoBar
import com.example.datomate.Components.PrimaryButton
import com.example.datomate.Components.PrimaryTextField
import com.example.datomate.Components.Space
import com.example.datomate.Components.SpaceW

@Composable
fun PassResetView(
    onLoginClick: () -> Unit
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
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 70.dp, start = 20.dp, end = 20.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                ContentPassResetView(onLoginClick)
            }
        }
    }
}

@Composable
fun ContentPassResetView(
    onLoginClick: () -> Unit
){
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 40.dp)
    ) {
        // Título
        Text(
            "Cambia tu contraseña",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary // color_primary_green
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campos de texto
        PrimaryTextField(
            label = "Correo electronico",
            value = email,
            onValueChange = { email = it }
        )
        Space(16)
        PrimaryTextField(
            label = "Codigo de verificacion",
            value = code,
            onValueChange = { code = it }
        )
        Space(16)

        // Contraseña y Repetir (Usando Row, con weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PrimaryTextField(
                label = "Contraseña",
                value = newPassword,
                onValueChange = { newPassword = it },
                modifier = Modifier.weight(1f) // Se asegura que ocupe la mitad
            )
            SpaceW(16) // Espacio entre los campos
            PrimaryTextField(
                label = "Repetir",
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                modifier = Modifier.weight(1f) // Se asegura que ocupe la mitad
            )
        }

        Space(32)

        // Botón de Actualizar Contraseña
        PrimaryButton(
            text = "Actualizar contraseña",
            onClick = { /* Lógica de actualización */ }
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