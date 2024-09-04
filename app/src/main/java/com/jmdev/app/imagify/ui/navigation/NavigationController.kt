package com.jmdev.app.imagify.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.screens.imagedetail.ImageDetail
import com.jmdev.app.imagify.ui.screens.mainscreen.MainScreen
import com.jmdev.app.imagify.ui.screens.settings.Settings
import com.jmdev.app.imagify.ui.theme.ImagifyTheme
import kotlinx.coroutines.launch

@Composable
fun NavigationController(
    permissionRequest: () -> Unit,
    homePhotos: LazyPagingItems<Photo>,
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val homeLazyState = rememberLazyListState()
    val searchLazyState = rememberLazyListState()
    val shouldRefresh = remember { mutableStateOf(false) }
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
                        navigateToDetail = {
                            navController.navigate(
                                NavigationRoutes.ImageDetail.route
                            ) {
                                launchSingleTop = true
                            }
                        },
                        homePhotos = homePhotos,
                        lazyState = homeLazyState,
                        searchLazyState = searchLazyState,
                        onRefresh = {
                            scope.launch {
                                homeLazyState.animateScrollToItem(0)
                            }
                        },
                        shouldRefresh = shouldRefresh
                    )
                }
                composable(route = NavigationRoutes.ImageDetail.route) {
                    ImageDetail(
                        navigateToHome = { navController.popBackStack() },
                        permissionRequest = { permissionRequest() }
                    )
                }
                composable(route = NavigationRoutes.Settings.route) {
                    Settings(
                        navigateToHome = {
                            navController.popBackStack()
                        },
                        shouldRefresh = shouldRefresh,
                        scope = scope,
                        searchLazyState = searchLazyState
                    )
                }
            }
        }
    }
}