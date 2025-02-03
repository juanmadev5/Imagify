package com.jmdev.app.imagify.presentation.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.App.Companion.SEARCH_KEYWORDS
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.data.UserPreferences
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.presentation.paging.HomePagingSource
import com.jmdev.app.imagify.presentation.paging.SearchPagingSource
import com.jmdev.app.imagify.utils.PhotoQuality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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

    var photoQuality = MutableStateFlow(App.DEFAULT_PHOTO_QUALITY)
        private set

    var downloadQuality = MutableStateFlow(App.DEFAULT_DOWNLOAD_QUALITY)
        private set

    var searchPhotoOrientation = MutableStateFlow(App.DEFAULT_PHOTO_ORIENTATION)
        private set

    private val _homeRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    val homePhotos = _homeRefreshTrigger
        .pagerTrigger(Pager(PagingConfig(pageSize = 4)) {
            HomePagingSource(
                photoRepository
            )
        })
        .stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())

    val searchPhotos = searchQuery
        .flatMapLatest { query ->
            Pager(PagingConfig(pageSize = 4)) {
                SearchPagingSource(photoRepository, query)
            }.flow.cachedIn(viewModelScope)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())

    init {
        viewModelScope.launch {
            userPreferences.onReadDataRequest(
                getSaveQuality = {
                    photoQuality.value = it
                },
                getDownloadQuality = {
                    downloadQuality.value = it
                },
                getSearchOrientation = {
                    searchPhotoOrientation.value = it
                }
            )
        }
    }

    fun queryPhotos(query: String) {
        searchQuery.value = query
    }

    fun setQuality(quality: PhotoQuality) =
        updateSetting(quality, photoQuality, userPreferences::saveQuality)

    fun setDownloadQuality(quality: PhotoQuality) =
        updateSetting(quality, downloadQuality, userPreferences::saveDownloadQuality)

    fun setSearchOrientation(orientation: String) =
        updateSetting(orientation, searchPhotoOrientation, userPreferences::saveSearchOrientation)

    private fun MutableSharedFlow<Unit>.pagerTrigger(pager: Pager<Int, FeedPhoto>) =
        onStart { emit(Unit) }
            .flatMapLatest {
                flow {
                    emitAll(
                        pager.flow.cachedIn(viewModelScope)
                    )
                }
            }.stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())

    private fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    private fun <T> updateSetting(
        value: T,
        stateFlow: MutableStateFlow<T>,
        save: suspend (T) -> Unit,
    ) {
        stateFlow.value = value
        launchIO { save(value) }
    }
}