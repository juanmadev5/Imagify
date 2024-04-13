package com.jmdev.app.imagify.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.jmdev.app.imagify.ui.navigation.NavigationController
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val appViewModel by viewModels<AppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().setKeepOnScreenCondition {
            appViewModel.splashScreenState.value
        }
        appViewModel.fetchPhotos()
        setContent {
            NavigationController(appViewModel = appViewModel)
        }
    }
}