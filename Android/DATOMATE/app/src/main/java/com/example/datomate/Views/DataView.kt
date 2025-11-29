package com.example.datomate.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datomate.Components.*
import com.example.datomate.R // Importar tus iconos
import com.example.datomate.Navigation.Screen // Para la navegación

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataView(
    navController: NavController,
) {

    var currentScreen by remember { mutableStateOf(Screen.DataEntry.route) }

    val navItems = listOf(
        BottomNavItem(R.drawable.home, Screen.Home.route),
        BottomNavItem(R.drawable.captura, Screen.DataEntry.route),
        BottomNavItem(R.drawable.prediccion, Screen.Summary.route),
        BottomNavItem(R.drawable.reportes, Screen.Reports.route),
        BottomNavItem(R.drawable.imagenes, "image"),
        BottomNavItem(R.drawable.perfil, Screen.Profile.route)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {AppLogoBar(100, Alignment.TopStart)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        // La barra inferior modularizada
        bottomBar = {
            BottomNavBar(
                items = navItems,
                currentRoute = currentScreen,
                onItemSelected = { route ->
                    currentScreen = route
                    navController.navigate(route) {
                    // Esto evita múltiples instancias de la misma pantalla
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            ContentDataView()
        }


    }
}

@Composable
fun ContentDataView(){

    var tempValue by remember { mutableStateOf("") }
    var humidityValue by remember { mutableStateOf("") }
    var stemLengthValue by remember { mutableStateOf("") }
    var stemDiameterValue by remember { mutableStateOf("") }

    //Tarjeta de Fecha
    DateMonitorCard(date = "Jueves 25 Noviembre 2025")

    // Card principal de registro (Fondo blanco)
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 70.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Título
            Text(
                "Hacer nuevo registro.",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Campos con iconos (Componente IconTextField)
            IconTextField(
                label = "Temperatura",
                value = tempValue,
                onValueChange = { tempValue = it },
                iconPainter = painterResource(id = R.drawable.termometro) // Placeholder
            )
            Space(16)
            IconTextField(
                label = "Humedad",
                value = humidityValue,
                onValueChange = { humidityValue = it },
                iconPainter = painterResource(id = R.drawable.gota) // Placeholder
            )
            Space(16)
            IconTextField(
                label = "Longitud promedio del tallo",
                value = stemLengthValue,
                onValueChange = { stemLengthValue = it },
                iconPainter = painterResource(id = R.drawable.regla) // Placeholder
            )
            Space(16)
            IconTextField(
                label = "Diametro del tallo promedio",
                value = stemDiameterValue,
                onValueChange = { stemDiameterValue = it },
                iconPainter = painterResource(id = R.drawable.compass) // Placeholder
            )

            Space(32)

            // Botón principal
            PrimaryButton(
                text = "Hacer registro",
                onClick = { /* Lógica de guardado */ }
            )
        }
    }
}


