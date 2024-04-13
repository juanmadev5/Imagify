package com.jmdev.app.imagify.ui.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jmdev.app.imagify.ui.navigation.core.LocalNavigationController
import com.jmdev.app.imagify.ui.navigation.core.NavigationRoutes
import com.jmdev.app.imagify.ui.screens.HomeScreen
import com.jmdev.app.imagify.ui.screens.ImageDetail
import com.jmdev.app.imagify.ui.screens.Settings
import com.jmdev.app.imagify.ui.theme.ImagifyTheme
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun NavigationController(appViewModel: AppViewModel) {
    val navController = rememberNavController()
    ImagifyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CompositionLocalProvider(LocalNavigationController provides navController) {
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.Home.route,
                    enterTransition = {
                        scaleIn(spring(Spring.DampingRatioNoBouncy))
                    },
                    exitTransition = {
                        scaleOut(spring(Spring.DampingRatioNoBouncy))
                    },
                    popEnterTransition = {
                        scaleIn(spring(Spring.DampingRatioNoBouncy))
                    },
                    popExitTransition = {
                        scaleOut(spring(Spring.DampingRatioNoBouncy))
                    }
                ) {
                    composable(route = NavigationRoutes.Home.route) {
                        HomeScreen(appViewModel = appViewModel)
                    }
                    composable(route = NavigationRoutes.ImageDetail.route) {
                        ImageDetail(appViewModel = appViewModel)
                    }
                    composable(route = NavigationRoutes.Settings.route) {
                        Settings(appViewModel = appViewModel)
                    }
                }
            }
        }
    }
}