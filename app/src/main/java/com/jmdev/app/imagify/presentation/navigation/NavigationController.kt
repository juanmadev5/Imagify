package com.jmdev.app.imagify.presentation.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.jmdev.app.imagify.presentation.screens.imageDetailScreen.ImageDetail
import com.jmdev.app.imagify.presentation.screens.mainScreen.MainScreen
import com.jmdev.app.imagify.presentation.screens.settingsScreen.Settings
import com.jmdev.app.imagify.presentation.screens.settingsScreen.SettingsViewModel
import com.jmdev.app.imagify.presentation.theme.ImagifyTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationController() {
    val backStack = rememberNavBackStack(Main)
    fun goBack() {
        backStack.removeLastOrNull()
    }

    val settingsViewModel: SettingsViewModel = koinViewModel()
    val photoQuality by settingsViewModel.photoQuality.collectAsState()

    ImagifyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavDisplay(
                backStack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator()
                ),
                entryProvider = entryProvider {
                    entry<Main> {
                        MainScreen(
                            navigateToSettings = {
                                backStack.add(Settings)
                            },
                            navigateToDetail = { photoId, _ ->
                                backStack.add(Details(photoId))
                            }
                        )
                    }
                    entry<Details> { key ->
                        ImageDetail(
                            navigateToHome = { goBack() },
                            photoId = key.id,
                            quality = photoQuality
                        )
                    }
                    entry<Settings> {
                        Settings(
                            navigateToHome = {
                                goBack()
                            }
                        )
                    }
                },
                transitionSpec = {
                    ContentTransform(
                        slideInHorizontally() + fadeIn(),
                        slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
                    )
                },
                popTransitionSpec = {
                    ContentTransform(
                        slideInHorizontally() + fadeIn(),
                        slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
                    )
                },
                predictivePopTransitionSpec = {
                    ContentTransform(
                        slideInHorizontally() + fadeIn(),
                        slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
                    )
                }
            )
        }
    }
}

