package com.jmdev.app.imagify.presentation.screens.imagedetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.utils.PermissionManager
import com.jmdev.app.imagify.utils.PhotoDownloadManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val downloadManager: PhotoDownloadManager,
    private val permissionManager: PermissionManager,
) : ViewModel() {

    val isPermissionGranted = MutableStateFlow(false)

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
}