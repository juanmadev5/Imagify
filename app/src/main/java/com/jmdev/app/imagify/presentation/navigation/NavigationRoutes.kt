package com.jmdev.app.imagify.presentation.navigation

sealed class NavigationRoutes(val route: String) {
    data object Home : NavigationRoutes(route = "home")
    data object ImageDetail : NavigationRoutes(route = "detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
    data object Settings : NavigationRoutes(route = "settings")
}