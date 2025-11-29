package com.example.datomate.Navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.datomate.Views.DataView
import com.example.datomate.Views.EditProfileView
import com.example.datomate.Views.ForgotPassView
import com.example.datomate.Views.HomeView
import com.example.datomate.Views.LoginView
import com.example.datomate.Views.PassResetView
import com.example.datomate.Views.ProfileView
import com.example.datomate.Views.RecoveryType
import com.example.datomate.Views.RegisterView
import com.example.datomate.Views.ReportsView
import com.example.datomate.Views.SummaryView

@Composable
fun NavManager() {
    val navController = rememberNavController()

    // Estado para manejar si la recuperación es por Email o Teléfono,
    // ya que ambas usan la misma ruta/Composable (ForgotPassView).
    var recoveryType by remember { mutableStateOf(RecoveryType.EMAIL) }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // --- 1. Login View ---
        composable(Screen.Login.route) {
            LoginView(
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                onDataClick = {navController.navigate(Screen.DataEntry.route)}
            )
        }

        // --- 2. Register View ---
        composable(Screen.Register.route) {
            RegisterView(
                onLoginClick = { navController.navigate(Screen.Login.route) }
            )
        }

        // --- 3. Forgot Password View (Base) ---
        composable(Screen.ForgotPassword.route) {
            ForgotPassView(
                type = recoveryType,

                // Lógica para cambiar entre Email y Teléfono (NO navega, solo cambia el estado)
                onSwitchMethodClick = {
                    recoveryType = if (recoveryType == RecoveryType.EMAIL) {
                        RecoveryType.PHONE
                    } else {
                        RecoveryType.EMAIL
                    }
                },

                // Lógica para volver a la pantalla de Login
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        // Opcional: Esto limpia la pila de retroceso si el usuario ya no necesita volver aquí
                        // popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        // --- 4. Password Reset View ---
        composable(Screen.PasswordReset.route) {
            PassResetView(
                onLoginClick = { navController.navigate(Screen.Login.route) }
            )
        }

        // --- 5. Data View ---
        composable  (Screen.DataEntry.route){
            DataView(navController)
        }

        // --- 6. Summary View ---
        composable(Screen.Summary.route) {
            SummaryView(navController)
        }

        // --- 7. Profile View ---
        composable(Screen.Profile.route) {
            ProfileView(navController,
                onClickEditProfile = {navController.navigate(Screen.EditProfile.route)}
            )
        }
        // --- 8. Reports View ---
        composable(Screen.Reports.route) {
            ReportsView(navController)
        }
        // --- 9. Home View ---
        composable(Screen.Home.route) {
            HomeView(navController,
                onClickSummary = {navController.navigate(Screen.Summary.route)}
            )
        }
        // --- 10. Edit Profile
        composable(Screen.EditProfile.route) {
            EditProfileView(navController)
        }
    }
}