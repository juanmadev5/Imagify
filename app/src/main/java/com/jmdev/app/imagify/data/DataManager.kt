package com.jmdev.app.imagify.data

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import javax.inject.Inject

class DataManager @Inject constructor() {

    fun downloadPhoto(context: Context, link: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(link))
        request.setTitle(fileName)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ".jpeg")
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
}