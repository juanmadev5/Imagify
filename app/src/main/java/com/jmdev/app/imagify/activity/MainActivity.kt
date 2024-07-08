package com.jmdev.app.imagify.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jmdev.app.imagify.ui.navigation.NavigationController
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appViewModel by viewModels<AppViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.parseColor("#801b1b1b"),
                Color.parseColor("#801b1b1b")
            )
        )
        installSplashScreen().setKeepOnScreenCondition {
            appViewModel.splashScreenState.value
        }
        setContent {
            NavigationController(appViewModel = appViewModel)
        }
    }
}