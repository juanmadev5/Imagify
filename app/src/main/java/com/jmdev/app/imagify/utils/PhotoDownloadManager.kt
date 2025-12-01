package com.jmdev.app.imagify.utils

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import com.jmdev.app.imagify.R

class PhotoDownloadManager {

    fun downloadPhoto(
        context: Context,
        photoUrl: String,
        fileName: String,
    ) {
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
    }
}
