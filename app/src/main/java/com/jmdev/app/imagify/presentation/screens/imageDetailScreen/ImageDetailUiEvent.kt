package com.jmdev.app.imagify.presentation.screens.imageDetailScreen

import android.content.Context

sealed class ImageDetailUiEvent {
    data class DownloadPhoto(
        val context: Context,
        val link: String,
        val fileName: String,
    ) : ImageDetailUiEvent()
}
