package com.jmdev.app.imagify.presentation.screens.imageDetailScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.model.photo.User
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.utils.PermissionManager
import com.jmdev.app.imagify.utils.PhotoDownloadManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ImageDetailViewModel (
    private val photoRepository: PhotoRepository,
    private val downloadManager: PhotoDownloadManager,
    private val permissionManager: PermissionManager,
) : ViewModel() {

    val isPermissionGranted = MutableStateFlow(false)
    val userProfile = MutableStateFlow<User?>(null)

    private val _photo = MutableStateFlow<Photo?>(null)
    val photo: StateFlow<Photo?> = _photo
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(50), null)

    fun getPhoto(id: String) {
        viewModelScope.launch {
            _photo.value = photoRepository.getPhoto(id)
        }
    }

    fun setPermissionGranted() {
        val granted = permissionManager.isPermissionGranted.value
        isPermissionGranted.value = granted
    }

    fun downloadPhoto(context: Context, link: String, fileName: String) {
        viewModelScope.launch {
            downloadManager.downloadPhoto(context, link, fileName, isPermissionGranted.value)
        }
    }

    fun getUserProfile(username: String) {
        viewModelScope.launch {
            userProfile.value = photoRepository.getUserProfile(username)
        }
    }
}