package com.jmdev.app.imagify.presentation.screens.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.imagify.domain.usecase.GetUserPreferencesUseCase
import com.jmdev.app.imagify.domain.usecase.SaveOrientationUseCase
import com.jmdev.app.imagify.domain.usecase.SaveQualityUseCase
import com.jmdev.app.imagify.utils.launchIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingsViewModel(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val saveQualityUseCase: SaveQualityUseCase,
    private val saveOrientationUseCase: SaveOrientationUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val (quality, orientation) = getUserPreferencesUseCase()
            _uiState.update { it.copy(photoQuality = quality, searchPhotoOrientation = orientation) }
        }
    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.SetQuality -> {
                _uiState.update { it.copy(photoQuality = event.quality) }
                viewModelScope.launchIO { saveQualityUseCase(event.quality) }
            }
            is SettingsUiEvent.SetOrientation -> {
                Timber.i("orientation: %s", event.orientation)
                _uiState.update { it.copy(searchPhotoOrientation = event.orientation) }
                viewModelScope.launchIO { saveOrientationUseCase(event.orientation) }
            }
        }
    }
}
