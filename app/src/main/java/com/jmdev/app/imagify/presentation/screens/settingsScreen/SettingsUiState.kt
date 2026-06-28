package com.jmdev.app.imagify.presentation.screens.settingsScreen

import com.jmdev.app.imagify.DEFAULT_PHOTO_ORIENTATION
import com.jmdev.app.imagify.DEFAULT_PHOTO_QUALITY
import com.jmdev.app.imagify.utils.PhotoQuality

data class SettingsUiState(
    val photoQuality: PhotoQuality = DEFAULT_PHOTO_QUALITY,
    val searchPhotoOrientation: String = DEFAULT_PHOTO_ORIENTATION,
)
