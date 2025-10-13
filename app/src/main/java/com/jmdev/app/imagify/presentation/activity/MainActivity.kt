package com.jmdev.app.imagify.presentation.activity

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jmdev.app.imagify.presentation.navigation.NavigationController
import com.jmdev.app.imagify.utils.PermissionManager
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val permissionManager: PermissionManager by inject()

    private val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                permissionManager.setPermissionGranted(true)
            } else {
                permissionManager.setPermissionGranted(false)
                permissionManager.showPermissionDeniedDialog(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            NavigationController(
                permissionRequest = {
                    permissionManager.permissionRequest(
                        this,
                        request = { requestPermission() })
                },
            )
        }
    }

    private fun requestPermission() {
        permissionManager.onStart(
            request = { requestPermissionLauncher.launch(storagePermission) },
            this
        )
    }
}
