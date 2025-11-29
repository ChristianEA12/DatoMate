package com.example.datomate.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datomate.Components.*
import com.example.datomate.Navigation.Screen
import com.example.datomate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    onClickSummary: () -> Unit
) {

    val currentCrop = remember { mutableStateOf("Cultivo 1") }

    val avgTemp = "25ºC"
    val avgHumidity = "75%"
    val avgLength = "15 cm"
    val avgDiameter = "10 cm"

    var currentScreen by remember { mutableStateOf(Screen.Home.route) }

    val navItems = listOf(
        BottomNavItem(R.drawable.home, Screen.Home.route), // Home / Resumen
        BottomNavItem(R.drawable.captura, Screen.DataEntry.route),
        BottomNavItem(R.drawable.prediccion, Screen.Summary.route), // Ícono del gráfico (Reportes/Gráfica)
        BottomNavItem(R.drawable.reportes, Screen.Reports.route), // Ícono del archivo
        BottomNavItem(R.drawable.imagenes, "image"),
        BottomNavItem(R.drawable.perfil, Screen.Profile.route)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, // Fondo gris claro

        topBar = {
            TopAppBar(
                title = { AppLogoBar(100, Alignment.TopStart) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Verde oscuro
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* Acción para agregar */ }) {
                        Icon(painterResource(R.drawable.add_icon), contentDescription = "Agregar", tint = Color.White)
                    }
                }
            )
        },

        bottomBar = {
            BottomNavBar(
                items = navItems,
                currentRoute = currentScreen,
                onItemSelected = { route ->
                    currentScreen = route
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Selector de Cultivo Actual
            item {
                CropSelectorCard(currentCrop = currentCrop.value)
            }

            // 2. Tarjeta de Sugerencia
            item {
                SuggestionCard(
                    suggestionText = "Aquí sugerencia del Dia"
                )
            }

            // 3. Tarjeta de Datos Promedio del Día Anterior
            item {
                DataAverageCard(
                    avgTemp = avgTemp,
                    avgHumidity = avgHumidity,
                    avgLength = avgLength,
                    avgDiameter = avgDiameter,
                    onRegisterClick = {
                        navController.navigate(Screen.DataEntry.route)
                    }
                )
            }

            // 4. Botón "Ver predicción"
            item {
                PrimaryButton(
                    text = "Ver predicción",
                    onClick = onClickSummary
                )
            }
        }
    }
}