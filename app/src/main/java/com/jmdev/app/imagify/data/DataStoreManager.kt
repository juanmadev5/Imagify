package com.jmdev.app.imagify.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.core.PhotoQuality
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        private const val KEY_QUALITY = "quality"
        private const val KEY_DOWNLOAD_QUALITY = "download-quality"
        private const val KEY_ORIENTATION = "orientation"
    }

    private data class PreferencesData(
        val saveQuality: PhotoQuality,
        val downloadQuality: PhotoQuality,
        val searchOrientation: String,
    )

    private suspend fun savePreference(key: String, value: String) {
        dataStore.edit { pref ->
            pref[stringPreferencesKey(key)] = value
        }
    }

    private fun getPreferencesData(): Flow<PreferencesData> {
        return dataStore.data.map { pref ->
            PreferencesData(
                saveQuality = pref[stringPreferencesKey(KEY_QUALITY)]?.let { PhotoQuality.valueOf(it) }
                    ?: PhotoQuality.REGULAR,
                downloadQuality = pref[stringPreferencesKey(KEY_DOWNLOAD_QUALITY)]?.let {
                    PhotoQuality.valueOf(
                        it
                    )
                }
                    ?: PhotoQuality.RAW,
                searchOrientation = pref[stringPreferencesKey(KEY_ORIENTATION)]
                    ?: App.DEFAULT_PHOTO_ORIENTATION
            )
        }
    }

    suspend fun saveQuality(quality: PhotoQuality) {
        savePreference(KEY_QUALITY, quality.toString())
    }

    suspend fun saveDownloadQuality(quality: PhotoQuality) {
        savePreference(KEY_DOWNLOAD_QUALITY, quality.toString())
    }

    suspend fun saveSearchOrientation(orientation: String) {
        savePreference(KEY_ORIENTATION, orientation)
    }

    suspend fun onReadDataRequest(
        getSaveQuality: (PhotoQuality) -> Unit,
        getDownloadQuality: (PhotoQuality) -> Unit,
        getSearchOrientation: (String) -> Unit,
    ) {
        getPreferencesData().first().apply {
            getSaveQuality(saveQuality)
            getDownloadQuality(downloadQuality)
            getSearchOrientation(searchOrientation)
        }
    }
}