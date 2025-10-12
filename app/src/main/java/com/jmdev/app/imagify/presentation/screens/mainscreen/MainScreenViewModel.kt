package com.jmdev.app.imagify.presentation.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmdev.app.imagify.DEFAULT_PHOTO_ORIENTATION
import com.jmdev.app.imagify.DEFAULT_PHOTO_QUALITY
import com.jmdev.app.imagify.SEARCH_KEYWORDS
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.data.UserPreferences
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.presentation.paging.HomePagingSource
import com.jmdev.app.imagify.presentation.paging.SearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    var searchQuery = MutableStateFlow(SEARCH_KEYWORDS[Random.nextInt(SEARCH_KEYWORDS.size)])
        private set

    var photoQuality = MutableStateFlow(DEFAULT_PHOTO_QUALITY)
        private set

    private val _searchPhotoOrientation = MutableStateFlow(DEFAULT_PHOTO_ORIENTATION)

    val searchPhotoOrientation: StateFlow<String> = _searchPhotoOrientation
        .stateIn(viewModelScope, SharingStarted.Lazily, DEFAULT_PHOTO_ORIENTATION)

    private val _homeRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    private val _searchRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    val homePhotos = _homeRefreshTrigger
        .pagerTrigger(Pager(PagingConfig(pageSize = 4)) {
            HomePagingSource(
                photoRepository
            )
        })
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .onStart { onLoad() }

    val searchPhotos = combine(searchQuery, _searchRefreshTrigger) { query, _ ->
        query
    }.flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 4)) {
            SearchPagingSource(photoRepository, query, _searchPhotoOrientation.value)
        }.flow.cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun onLoad() {
        viewModelScope.launch {
            userPreferences.onReadDataRequest(
                getPhotoQuality = {
                    photoQuality.value = it
                },
                getSearchOrientation = {
                    _searchPhotoOrientation.value = it
                }
            )
            _searchRefreshTrigger.emit(Unit)
        }
    }

    fun queryPhotos(query: String) {
        searchQuery.value = query
    }

    private fun MutableSharedFlow<Unit>.pagerTrigger(pager: Pager<Int, FeedPhoto>) =
        onStart { emit(Unit) }
            .flatMapLatest {
                flow {
                    emitAll(
                        pager.flow.cachedIn(viewModelScope)
                    )
                }
            }.stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())
}