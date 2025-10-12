package com.jmdev.app.imagify.presentation.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.imagify.DEFAULT_PHOTO_ORIENTATION
import com.jmdev.app.imagify.DEFAULT_PHOTO_QUALITY
import com.jmdev.app.imagify.data.UserPreferences
import com.jmdev.app.imagify.utils.PhotoQuality
import com.jmdev.app.imagify.utils.updateSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
) : ViewModel() {

    var photoQuality = MutableStateFlow(DEFAULT_PHOTO_QUALITY)
        private set

    var searchPhotoOrientation = MutableStateFlow(DEFAULT_PHOTO_ORIENTATION)
        private set

    init {
        viewModelScope.launch {
            userPreferences.onReadDataRequest(
                getPhotoQuality = {
                    photoQuality.value = it
                },
                getSearchOrientation = {
                    searchPhotoOrientation.value = it
                }
            )
        }
    }

    fun setQuality(quality: PhotoQuality) =
        updateSetting(
            quality,
            photoQuality,
            userPreferences::saveQuality,
            viewModelScope
        )

    fun setSearchOrientation(orientation: String) {
        Log.i("orientation", orientation)
        updateSetting(
            orientation,
            searchPhotoOrientation,
            userPreferences::saveSearchOrientation,
            viewModelScope
        )
    }
}