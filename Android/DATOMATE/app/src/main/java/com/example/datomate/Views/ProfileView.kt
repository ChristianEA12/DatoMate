package com.example.datomate.Views

import androidx.compose.foundation.layout.*
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
fun ProfileView(
    navController: NavController,
    onClickEditProfile: () -> Unit
) {
    // Estado para la navegación inferior (simulación)
    var currentScreen by remember { mutableStateOf(Screen.Profile.route) }

    // Definición de los ítems de la barra inferior (asumiendo el icono de persona es la vista actual)
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
                title = { AppLogoBar(100, Alignment.TopStart) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
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
        // Cuerpo principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {
            ProfileCard(
                username = "Username.",
                onEditClick = onClickEditProfile,
                onLogoutClick = { navController.navigate(Screen.Login.route) } // Ejemplo de navegación al salir
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}