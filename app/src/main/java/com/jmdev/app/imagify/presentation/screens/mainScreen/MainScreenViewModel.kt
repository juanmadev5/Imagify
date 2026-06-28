package com.jmdev.app.imagify.presentation.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.domain.usecase.GetUserPreferencesUseCase
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.utils.ConnectivityObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModel(
    private val photoRepository: PhotoRepository,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    val isOnline: StateFlow<Boolean> = connectivityObserver.isOnline

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState

    private val _searchRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    val homePhotos: StateFlow<PagingData<FeedPhoto>> = photoRepository.getFeedPhotos()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())

    val searchPhotos: StateFlow<PagingData<FeedPhoto>> =
        combine(_uiState.map { it.searchQuery }, _searchRefreshTrigger) { query, _ -> query }
            .flatMapLatest { query ->
                photoRepository.searchPhotos(query, _uiState.value.searchPhotoOrientation)
                    .cachedIn(viewModelScope)
            }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        viewModelScope.launch {
            val (quality, orientation) = getUserPreferencesUseCase()
            _uiState.update { it.copy(photoQuality = quality, searchPhotoOrientation = orientation) }
            _searchRefreshTrigger.emit(Unit)
        }
    }

    fun onEvent(event: MainScreenUiEvent) {
        when (event) {
            is MainScreenUiEvent.QueryPhotos ->
                _uiState.update { it.copy(searchQuery = event.query) }
        }
    }
}
