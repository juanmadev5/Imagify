package com.jmdev.app.imagify.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jmdev.app.imagify.DEFAULT_PHOTO_ORIENTATION
import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository
import com.jmdev.app.imagify.utils.PhotoQuality
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : UserPreferencesRepository {

    companion object {
        private const val KEY_QUALITY = "quality"
        private const val KEY_ORIENTATION = "orientation"
    }

    override suspend fun getPhotoQuality(): PhotoQuality =
        dataStore.data.map { pref ->
            pref[stringPreferencesKey(KEY_QUALITY)]
                ?.let { PhotoQuality.valueOf(it) }
                ?: PhotoQuality.REGULAR
        }.first()

    override suspend fun getSearchOrientation(): String =
        dataStore.data.map { pref ->
            pref[stringPreferencesKey(KEY_ORIENTATION)] ?: DEFAULT_PHOTO_ORIENTATION
        }.first()

    override suspend fun saveQuality(quality: PhotoQuality) {
        dataStore.edit { pref ->
            pref[stringPreferencesKey(KEY_QUALITY)] = quality.toString()
        }
    }

    override suspend fun saveSearchOrientation(orientation: String) {
        dataStore.edit { pref ->
            pref[stringPreferencesKey(KEY_ORIENTATION)] = orientation
        }
    }
}
