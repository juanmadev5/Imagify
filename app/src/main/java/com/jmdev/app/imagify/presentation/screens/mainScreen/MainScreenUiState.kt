package com.jmdev.app.imagify.presentation.screens.mainScreen

import com.jmdev.app.imagify.DEFAULT_PHOTO_ORIENTATION
import com.jmdev.app.imagify.DEFAULT_PHOTO_QUALITY
import com.jmdev.app.imagify.SEARCH_KEYWORDS
import com.jmdev.app.imagify.utils.PhotoQuality
import kotlin.random.Random

data class MainScreenUiState(
    val searchQuery: String = SEARCH_KEYWORDS[Random.nextInt(SEARCH_KEYWORDS.size)],
    val searchPhotoOrientation: String = DEFAULT_PHOTO_ORIENTATION,
    val photoQuality: PhotoQuality = DEFAULT_PHOTO_QUALITY,
)
