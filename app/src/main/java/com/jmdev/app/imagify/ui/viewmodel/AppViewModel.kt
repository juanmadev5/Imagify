package com.jmdev.app.imagify.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.core.GetPhotosMode
import com.jmdev.app.imagify.core.Orientation
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.core.State
import com.jmdev.app.imagify.data.DataManager
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.model.photo.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val dataManager: DataManager
) : ViewModel() {

    private val _getPhotosMode = MutableStateFlow(App.DEFAULT_GET_PHOTOS_MODE)
    val getPhotosMode = _getPhotosMode

    private val _photos = MutableStateFlow<List<Photo?>?>(null)
    val photos = _photos

    private val _photo = MutableStateFlow<Photo?>(null)
    val photo = _photo

    private val _photoQuality = MutableStateFlow(App.DEFAULT_PHOTO_QUALITY)
    val photoQuality = _photoQuality

    private val _homePhotoOrientation = MutableStateFlow(App.DEFAULT_PHOTO_ORIENTATION)
    val homePhotoOrientation = _homePhotoOrientation

    private val _state = MutableStateFlow(State.WAITING)
    val state = _state

    private val _splashScreenState = MutableStateFlow(_state.value != State.WAITING)
    val splashScreenState = _splashScreenState

    private val _orderBy = MutableStateFlow(App.ORDER_BY_LATEST)
    val orderBy = _orderBy

    private val _downloadQuality = MutableStateFlow(App.DEFAULT_DOWNLOAD_QUALITY)
    val downloadQuality = _downloadQuality

    /*
    private val _searchPhotoOrientation = MutableStateFlow(App.DEFAULT_PHOTO_ORIENTATION)
    val searchPhotoOrientation = _searchPhotoOrientation
    */

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex


    fun fetchPhotos() {
        _state.value = State.WAITING
        if (_photos.value != null) {
            _photos.value = null
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (_getPhotosMode.value == GetPhotosMode.EDITORIAL_FEED) {
                val result =
                    photoRepository.getPhotos(_currentPage.value, _orderBy.value)
                result.onSuccess { photos ->
                    _photos.value = photos
                    _state.value = State.OK
                }
                result.onFailure { _ ->
                    _state.value = State.ERROR
                }
            } else {
                val orientation = when (_homePhotoOrientation.value) {
                    Orientation.PORTRAIT -> App.ORIENTATION_PORTRAIT
                    Orientation.LANDSCAPE -> App.ORIENTATION_LANDSCAPE
                    Orientation.SQUARISH -> App.ORIENTATION_SQUARISH
                }
                val result = photoRepository.getRandomPhotos(orientation)
                result.onSuccess { photos ->
                    _photos.value = photos
                    _state.value = State.OK
                }
                result.onFailure {
                    _state.value = State.ERROR
                }
            }
            _currentIndex.value = 0
        }
    }

    fun setPhoto(photo: Photo) {
        _photo.value = photo
    }

    fun setQuality(quality: PhotoQuality) {
        _photoQuality.value = quality
        fetchPhotos()
    }

    fun setDownloadQuality(quality: PhotoQuality) {
        _downloadQuality.value = quality
    }

    fun setHomeOrientation(orientation: Orientation) {
        _homePhotoOrientation.value = orientation
        fetchPhotos()
    }

    /*
    fun setSearchOrientation(orientation: Orientation) {
        _searchPhotoOrientation.value = orientation
    }
     */

    fun setOrder(orderBy: String) {
        _orderBy.value = orderBy
        fetchPhotos()
    }

    fun setGetPhotosMode(mode: GetPhotosMode) {
        _getPhotosMode.value = mode
        fetchPhotos()
    }

    fun getQuality(): String? {
        return when (_photoQuality.value) {
            PhotoQuality.RAW -> _photo.value?.urls?.raw
            PhotoQuality.FULL -> _photo.value?.urls?.full
            PhotoQuality.REGULAR -> _photo.value?.urls?.regular
        }
    }

    fun downloadPhoto(context: Context, link: String, fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataManager.downloadPhoto(context, link, fileName)
        }
    }
}