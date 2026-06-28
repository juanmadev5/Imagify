package com.jmdev.app.imagify.domain.usecase

import android.content.Context
import com.jmdev.app.imagify.utils.PhotoDownloadManager

class DownloadPhotoUseCase(private val downloadManager: PhotoDownloadManager) {
    suspend operator fun invoke(context: Context, link: String, fileName: String): Boolean =
        downloadManager.downloadPhoto(context, link, fileName)
}
