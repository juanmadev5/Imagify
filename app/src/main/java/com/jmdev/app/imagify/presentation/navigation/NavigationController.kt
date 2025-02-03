package com.jmdev.app.imagify.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jmdev.app.imagify.presentation.screens.imagedetail.ImageDetail
import com.jmdev.app.imagify.presentation.screens.mainscreen.MainScreen
import com.jmdev.app.imagify.presentation.screens.settings.Settings
import com.jmdev.app.imagify.presentation.theme.ImagifyTheme

@Composable
fun NavigationController(
    permissionRequest: () -> Unit,
) {
    val navController = rememberNavController()

    ImagifyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.Home.route
            ) {
                composable(route = NavigationRoutes.Home.route) {
                    MainScreen(
                        navigateToSettings = {
                            navController.navigate(
                                NavigationRoutes.Settings.route
                            ) {
                                launchSingleTop = true
                            }
                        },
                        navigateToDetail = { photoId, quality ->
                            navController.navigate(
                                NavigationRoutes.ImageDetail.route.replace(
                                    "{id}",
                                    photoId
                                ).replace("{quality}", quality)
                            ) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(
                    route = NavigationRoutes.ImageDetail.route,
                    arguments = listOf(
                        navArgument("id") { type = NavType.StringType },
                        navArgument("quality") { type = NavType.StringType }
                    ),
                ) {
                    val photoId = it.arguments?.getString("id") ?: ""
                    val photoQuality = it.arguments?.getString("quality") ?: ""
                    ImageDetail(
                        navigateToHome = { navController.popBackStack() },
                        permissionRequest = { permissionRequest() },
                        photoId = photoId,
                        photoQuality = photoQuality
                    )
                }
                composable(route = NavigationRoutes.Settings.route) {
                    Settings(
                        navigateToHome = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}