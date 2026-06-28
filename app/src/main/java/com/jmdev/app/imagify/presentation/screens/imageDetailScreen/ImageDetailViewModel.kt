package com.jmdev.app.imagify.presentation.screens.imageDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.imagify.domain.usecase.DownloadPhotoUseCase
import com.jmdev.app.imagify.domain.usecase.GetPhotoUseCase
import com.jmdev.app.imagify.domain.usecase.GetUserProfileUseCase
import com.jmdev.app.imagify.utils.ConnectivityObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

enum class DownloadStatus { IDLE, LOADING, SUCCESS, ERROR }

class ImageDetailViewModel(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ImageDetailUiState>(ImageDetailUiState.Loading)
    val uiState: StateFlow<ImageDetailUiState> = _uiState.asStateFlow()

    private val _downloadStatus = MutableStateFlow(DownloadStatus.IDLE)
    val downloadStatus: StateFlow<DownloadStatus> = _downloadStatus.asStateFlow()

    private var loadJob: kotlinx.coroutines.Job? = null

    fun loadPhoto(photoId: String) {
        loadJob?.cancel()
        _uiState.value = ImageDetailUiState.Loading
        loadJob = viewModelScope.launch {
            try {
                val photo = getPhotoUseCase(photoId)
                if (photo == null) {
                    _uiState.value = ImageDetailUiState.Error(isNetworkError = !connectivityObserver.isOnline.value)
                    return@launch
                }
                val userProfile = runCatching { getUserProfileUseCase(photo.user.username) }.getOrNull()
                _uiState.value = ImageDetailUiState.Success(photo, userProfile)
            } catch (e: IOException) {
                _uiState.value = ImageDetailUiState.Error(isNetworkError = true)
            } catch (e: Exception) {
                _uiState.value = ImageDetailUiState.Error(isNetworkError = false)
            }
        }
    }

    fun onEvent(event: ImageDetailUiEvent) {
        when (event) {
            is ImageDetailUiEvent.DownloadPhoto -> {
                if (_downloadStatus.value == DownloadStatus.LOADING) return
                viewModelScope.launch {
                    _downloadStatus.value = DownloadStatus.LOADING
                    val success = downloadPhotoUseCase(event.context, event.link, event.fileName)
                    _downloadStatus.value = if (success) DownloadStatus.SUCCESS else DownloadStatus.ERROR
                    delay(2000)
                    _downloadStatus.value = DownloadStatus.IDLE
                }
            }
        }
    }
}
