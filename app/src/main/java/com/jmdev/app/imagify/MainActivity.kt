package com.jmdev.app.imagify

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.paging.compose.collectAsLazyPagingItems
import com.jmdev.app.imagify.ui.navigation.NavigationController
import com.jmdev.app.imagify.ui.viewmodel.ImagifyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val imagifyViewModel by viewModels<ImagifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        imagifyViewModel.onCreate(
            request = { requestPermissionLauncher().launch(Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        )

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )

        setContent {
            val homePhotos = imagifyViewModel.homePhotos.collectAsLazyPagingItems()

            NavigationController(
                permissionRequest = {
                    permissionRequest()
                },
                homePhotos = homePhotos
            )
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.required_permission))
            .setMessage(getString(R.string.requested_permission_desc))
            .setPositiveButton(getString(R.string.go_to_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun permissionRequest() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                24
            )
        }
    }

    private fun requestPermissionLauncher() = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imagifyViewModel.setPermissionGranted(true)
        } else {
            imagifyViewModel.setPermissionGranted(false)
            showPermissionDeniedDialog()
        }
    }
}