package com.jmdev.app.imagify.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.core.Orientation
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.core.State
import com.jmdev.app.imagify.data.DataManager
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val dataManager: DataManager
) : ViewModel() {

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos

    private val _photoQuality = MutableStateFlow(PhotoQuality.FULL)
    val photoQuality = _photoQuality

    private val _homePhotoOrientation = MutableStateFlow(Orientation.LANDSCAPE)
    val homePhotoOrientation = _homePhotoOrientation

    private val _searchPhotoOrientation = MutableStateFlow(Orientation.LANDSCAPE)
    val searchPhotoOrientation = _searchPhotoOrientation

    private val _state = MutableStateFlow(State.WAITING)

    private val _getPhotoState = MutableStateFlow(State.WAITING)
    val getPhotoState: StateFlow<State> = _getPhotoState

    private val _splashScreenState = MutableStateFlow(_state.value != State.WAITING)
    val splashScreenState = _splashScreenState

    private val _perPage = MutableStateFlow(App.DEFAULT_MAX_QUANTITY)
    val perPage = _perPage

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage

    fun fetchPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = photoRepository.getPhotos(_currentPage.value, _perPage.value)
            result.onSuccess { photos ->
                _photos.value = photos
                _state.value = State.OK
            }
            result.onFailure { _ ->
                _state.value = State.ERROR
            }
        }
    }

    fun getPhoto(imageId: String): Photo? {
        var photo: Photo? = null
        viewModelScope.launch(Dispatchers.IO) {
            val result = photoRepository.getPhoto(imageId)
            result.onSuccess {
                photo = it
                _getPhotoState.value = State.OK
            }
            result.onFailure {
                _getPhotoState.value = State.ERROR
            }
        }
        return photo
    }

    fun setQuality(quality: PhotoQuality) {
        _photoQuality.value = quality
    }
    fun setHomeOrientation(orientation: Orientation) {
        _homePhotoOrientation.value = orientation
    }
    fun setSearchOrientation(orientation: Orientation) {
        _searchPhotoOrientation.value = orientation
    }
    fun downloadPhoto(context: Context, link: String, fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataManager.downloadPhoto(context, link, fileName)
        }
    }
}