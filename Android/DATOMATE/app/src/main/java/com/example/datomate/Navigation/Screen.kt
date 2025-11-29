package com.example.datomate.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.datomate.Views.ForgotPassView
import com.example.datomate.Views.LoginView
import com.example.datomate.Views.RegisterView
import com.example.datomate.Views.RecoveryType

// 1. Definición de Rutas
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object ForgotPassword : Screen("forgot_password")
    data object PasswordReset : Screen("password_reset")
    data object DataEntry : Screen("data_entry")
    data object Summary : Screen("summary")
    data object Profile : Screen("profile")
    data object Reports : Screen("reports")
    data object Home : Screen("home")
    data object EditProfile : Screen("edit_profile")
}


