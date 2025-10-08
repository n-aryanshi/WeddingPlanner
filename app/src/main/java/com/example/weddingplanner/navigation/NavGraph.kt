package com.example.weddingplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weddingplanner.view.screens.*

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // 1️⃣ Splash
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2️⃣ Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        // 3️⃣ Signup
        composable(Screen.Signup.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        // 4️⃣ Home
        composable(Screen.Home.route) {
            WeddingHomeScreen(
                onVenueClick = {
                    navController.navigate(Screen.VenueList.route)
                },
                onChecklistClick = {
                    navController.navigate(Screen.CheckList.route)
                }
            )
        }

        // 5️⃣ Venue List
        composable(Screen.VenueList.route) {
            VenueListScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // 6️⃣ Checklist
        composable(Screen.CheckList.route) {
            CheckListScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
