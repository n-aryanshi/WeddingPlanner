package com.example.weddingplanner.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object VenueList : Screen("venue_list")
    object CheckList : Screen("check_list")
}
