package com.jmdev.app.imagify.utils

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import com.jmdev.app.imagify.R
import javax.inject.Inject

class PhotoDownloadManager @Inject constructor() {

    fun downloadPhoto(
        context: Context,
        photoUrl: String,
        fileName: String,
        permissionGranted: Boolean,
    ) {
        if (permissionGranted) {
            val downloadUri = photoUrl.toUri()

            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(photoUrl)
            val finalFileName = if (fileExtension.isNullOrEmpty()) {
                "$fileName.jpg"
            } else {
                "$fileName.$fileExtension"
            }

            val request = DownloadManager.Request(downloadUri).apply {
                setTitle(finalFileName)
                setDescription(context.getString(R.string.downloading_photo))
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "/Imagify/$finalFileName"
                )
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }

            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
        } else {
            Log.e("PhotoDownloadManager", "Permission to write to storage is not granted.")
        }
    }
}
