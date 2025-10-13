package com.jmdev.app.imagify.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jmdev.app.imagify.presentation.screens.imageDetailScreen.ImageDetail
import com.jmdev.app.imagify.presentation.screens.mainScreen.MainScreen
import com.jmdev.app.imagify.presentation.screens.settingsScreen.Settings
import com.jmdev.app.imagify.presentation.screens.settingsScreen.SettingsViewModel
import com.jmdev.app.imagify.presentation.theme.ImagifyTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationController(
    permissionRequest: () -> Unit,
) {
    val navController = rememberNavController()

    val settingsViewModel: SettingsViewModel = koinViewModel()
    val photoQuality by settingsViewModel.photoQuality.collectAsState()
//    val photoOrientation by settingsViewModel.searchPhotoOrientation.collectAsState()

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
                        navigateToDetail = { photoId, url ->
                            navController.navigate(
                                NavigationRoutes.ImageDetail.createRoute(photoId)
                            ) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(route = NavigationRoutes.ImageDetail.route) {
                    ImageDetail(
                        navigateToHome = { navController.popBackStack() },
                        permissionRequest = { permissionRequest() },
                        photoId = it.arguments?.getString("id") ?: "",
                        quality = photoQuality
                    )
                }
                composable(route = NavigationRoutes.Settings.route) {
                    Settings(
                        settingsViewModel = settingsViewModel,
                        navigateToHome = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}