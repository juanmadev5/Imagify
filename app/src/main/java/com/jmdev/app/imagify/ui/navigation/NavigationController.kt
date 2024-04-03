package com.jmdev.app.imagify.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.ui.navigation.core.LocalNavigationController
import com.jmdev.app.imagify.ui.navigation.core.NavigationRoutes
import com.jmdev.app.imagify.ui.screens.ImageDetail
import com.jmdev.app.imagify.ui.theme.ImagifyTheme
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun NavigationController(modifier: Modifier = Modifier, appViewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    ImagifyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimatedVisibility(
                visible = appViewModel.splashScreenState.collectAsState().value,
                enter = fadeIn(tween(durationMillis = App.ANIMATION_SPEC)),
                exit = fadeOut(tween(durationMillis = App.ANIMATION_SPEC))
            ) {
                CompositionLocalProvider(LocalNavigationController provides navController) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoutes.Home.route,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(App.ANIMATION_SPEC)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                animationSpec = tween(App.ANIMATION_SPEC)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                animationSpec = tween(App.ANIMATION_SPEC)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                animationSpec = tween(App.ANIMATION_SPEC)
                            )
                        }
                    ) {
                        composable(route = NavigationRoutes.Home.route) {

                        }
                        composable(
                            route = NavigationRoutes.ImageDetail.route,
                            arguments = listOf(navArgument("{imageId}") { NavType.StringType })
                        ) {
                            val args = it.arguments!!
                            ImageDetail(imageId = args.getString("{imageId}")!!)
                        }
                        composable(route = NavigationRoutes.Settings.route) {
                            
                        }
                    }
                }
            }
        }
    }
}