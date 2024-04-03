package com.jmdev.app.imagify.ui.navigation.core

sealed class NavigationRoutes(val route: String) {
    data object Home : NavigationRoutes(route = "home")
    data object ImageDetail : NavigationRoutes(route = "imageDetail/{imageId}")
    data object Settings : NavigationRoutes(route = "settings")
}