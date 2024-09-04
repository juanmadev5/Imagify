package com.jmdev.app.imagify.ui.viewmodel

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.App.Companion.SEARCH_KEYWORDS
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.data.DataStoreManager
import com.jmdev.app.imagify.data.PhotoDownloadManager
import com.jmdev.app.imagify.data.repository.HomePagingSource
import com.jmdev.app.imagify.data.repository.SearchPagingSource
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.network.UnsplashAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ImagifyViewModel @Inject constructor(
    private val photoDownloadManager: PhotoDownloadManager,
    private val api: UnsplashAPI,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    val isPermissionGranted = MutableStateFlow(false)

    private val _query = MutableStateFlow(SEARCH_KEYWORDS[Random.nextInt(SEARCH_KEYWORDS.size)])
    val query = _query

    private val _photo = MutableStateFlow<Photo?>(null)
    val photo: StateFlow<Photo?> = _photo

    private val _photoQuality = MutableStateFlow(App.DEFAULT_PHOTO_QUALITY)
    val photoQuality: StateFlow<PhotoQuality> = _photoQuality

    private val _downloadQuality = MutableStateFlow(App.DEFAULT_DOWNLOAD_QUALITY)
    val downloadQuality: StateFlow<PhotoQuality> = _downloadQuality

    private val _searchPhotoOrientation = MutableStateFlow(App.DEFAULT_PHOTO_ORIENTATION)
    val searchPhotoOrientation = _searchPhotoOrientation

    private val _homeRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    private val _searchRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    val homePhotos = _homeRefreshTrigger
        .pagerTrigger(Pager(PagingConfig(pageSize = 4)) {
            HomePagingSource(
                apiService = api,
            )
        })

    val searchPhotos = _searchRefreshTrigger
        .pagerTrigger(Pager(PagingConfig(pageSize = 4)) {
            SearchPagingSource(
                apiService = api,
                query = _query.value
            )
        })

    fun onCreate(
        request: () -> Unit,
    ) {
        launchIO {
            dataStoreManager.onReadDataRequest(
                getSaveQuality = {
                    _photoQuality.value = it
                },
                getDownloadQuality = {
                    _downloadQuality.value = it
                },
                getSearchOrientation = {
                    _searchPhotoOrientation.value = it
                }
            )
            withContext(Dispatchers.Main) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                    setPermissionGranted(true)
                } else {
                    request()
                }
            }
        }
    }

    fun setPermissionGranted(granted: Boolean) {
        isPermissionGranted.value = granted
    }

    private fun refreshSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchRefreshTrigger.emit(Unit)
        }
    }

    fun queryPhotos(query: String) {
        _query.value = query
        refreshSearch()
    }

    fun setPhoto(photo: Photo) {
        _photo.value = photo
    }

    fun setQuality(quality: PhotoQuality) =
        updateSetting(quality, _photoQuality, dataStoreManager::saveQuality)

    fun setDownloadQuality(quality: PhotoQuality) =
        updateSetting(quality, _downloadQuality, dataStoreManager::saveDownloadQuality)

    fun setSearchOrientation(orientation: String) =
        updateSetting(orientation, _searchPhotoOrientation, dataStoreManager::saveSearchOrientation)

    fun getQuality(): String? {
        return when (_photoQuality.value) {
            PhotoQuality.RAW -> _photo.value?.urls?.raw
            PhotoQuality.FULL -> _photo.value?.urls?.full
            PhotoQuality.REGULAR -> _photo.value?.urls?.regular
        }
    }

    fun downloadPhoto(context: Context, link: String, fileName: String) {
        launchIO {
            photoDownloadManager.downloadPhoto(context, link, fileName)
        }
    }

    private fun MutableSharedFlow<Unit>.pagerTrigger(pager: Pager<Int, Photo>) =
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