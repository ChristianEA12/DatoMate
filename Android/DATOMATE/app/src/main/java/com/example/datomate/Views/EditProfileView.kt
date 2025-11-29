package com.example.datomate.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datomate.Components.*
import com.example.datomate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileView(
    navController: NavController,
) {
    // 1. Estados para los campos editables
    var name by remember { mutableStateOf("Introduzca su nombre") }
    var description by remember { mutableStateOf("Descripcion. Lorem in due to core no domeso lasdemon con soldianom de soperta no mina") }
    var language by remember { mutableStateOf("Español") }
    var country by remember { mutableStateOf("México") }

    // El color de fondo verde de la cabecera es el color primario/terciario (dependiendo de tu tema)
    val headerBackground = MaterialTheme.colorScheme.primary // Verde oscuro/Principal

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            // 2. TopAppBar con botones Atrás y Guardar
            TopAppBar(
                title = { /* Título vacío */ },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Lógica de Guardado */ }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Guardar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 3. Cabecera (Avatar y Fondo Verde)
            item {
                EditProfileHeader(
                    headerBackground = headerBackground,
                    avatar = painterResource(id = R.drawable.perfil)
                )
            }

            // 4. Bloques de Información (General, Idioma, Cuenta)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 24.dp)
                ) {

                    // Bloque General
                    EditableInfoBlock(
                        title = "General",
                        content = {
                            PrimaryTextField(label = "Introduzca su nombre", value = name, onValueChange = { name = it })
                            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), thickness = 1.dp)
                            PrimaryTextField(label = "Descripción (opcional)", value = description, onValueChange = { description = it })
                        }
                    )

                    // --- Bloque Idioma y País ---
                    EditableInfoBlock(title = "Idioma y país",
                        content = {
                        PrimaryTextField(
                            label = "Seleccione su idioma en Datomate",
                            value = language,
                            onValueChange = { language = it }
                        )
                            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), thickness = 1.dp)
                        PrimaryTextField(
                            label = "País de la aplicación",
                            value = country,
                            onValueChange = { country = it }
                        )
                        }
                    )

                    // --- Bloque Cuenta (Borrar cuenta) ---
                    EditableInfoBlock(title = "Cuenta",
                        content = {
                            Text(
                                "Borrar cuenta",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                                color = Color.Red,
                                modifier = Modifier.clickable { /* Lógica para borrar cuenta */ }
                            )
                        }
                    )
                }
            }
        }
    }
}