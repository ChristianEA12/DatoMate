package com.example.datomate.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.datomate.Components.AccountNavigationLink
import com.example.datomate.Components.AppLogoBar
import com.example.datomate.Components.PrimaryButton
import com.example.datomate.Components.PrimaryTextField
import com.example.datomate.Components.Space
import com.example.datomate.Components.SpaceW
import com.example.datomate.Components.Title

@Composable
fun RegisterView(
    onLoginClick: () -> Unit
){
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
                ContentRegisterView(onLoginClick)
            }
        }
    }
}

@Composable
fun ContentRegisterView(
    onLoginClick: () -> Unit
){
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 40.dp)
    ) {
        Title("Registro")

        // Campos de texto
        PrimaryTextField(
            label = "Nombre de usuario",
            value = username,
            onValueChange = { username = it }
        )
        Space(16)
        PrimaryTextField(
            label = "Correo electrónico",
            value = email,
            onValueChange = { email = it }
        )
        Space(16)
        PrimaryTextField(
            label = "Teléfono",
            value = phone,
            onValueChange = { phone = it }
        )
        Space(16)

        // Contraseña y Repetir
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PrimaryTextField(
                label = "Contraseña",
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.weight(1f)
            )
            SpaceW(16)
            PrimaryTextField(
                label = "Repetir",
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                modifier = Modifier.weight(1f)
            )
        }

        Space(32)

        // Botón de Registrarse
        PrimaryButton(
            text = "Registrarse",
            onClick = { /* Lógica de Registro */ }
        )
        Space(24)

        // Enlace a Iniciar Sesión
        AccountNavigationLink(
            prefixText = "¿Ya tienes una cuenta? ",
            linkText = "Inicia sesión",
            onLinkClick = onLoginClick
        )

    }
}