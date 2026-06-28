package com.jmdev.app.imagify.presentation.screens.imageDetailScreen

import com.jmdev.app.imagify.model.photo.User
import com.jmdev.app.imagify.model.unsplashphoto.Photo

sealed class ImageDetailUiState {
    data object Loading : ImageDetailUiState()
    data class Success(val photo: Photo, val userProfile: User?) : ImageDetailUiState()
    data class Error(val isNetworkError: Boolean) : ImageDetailUiState()
}
