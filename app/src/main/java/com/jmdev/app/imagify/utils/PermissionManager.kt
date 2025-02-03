package com.jmdev.app.imagify.utils

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.jmdev.app.imagify.R
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor() {
    val isPermissionGranted = MutableStateFlow(false)

    fun setPermissionGranted(granted: Boolean) {
        isPermissionGranted.value = granted
    }

    fun onStart(request: () -> Unit, context: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            setPermissionGranted(true)
        } else {
            permissionRequest(context, request = { request() })
        }
    }

    fun permissionRequest(
        context: Context,
        request: () -> Unit,
    ) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            request()
        } else {
            setPermissionGranted(true)
        }
    }

    fun showPermissionDeniedDialog(context: Context) {
        AlertDialog.Builder(context).setTitle(context.getString(R.string.required_permission))
            .setMessage(context.getString(R.string.requested_permission_desc))
            .setPositiveButton(context.getString(R.string.go_to_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }.setNegativeButton(context.getString(R.string.cancel), null).show()
    }
}