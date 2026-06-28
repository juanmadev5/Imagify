package com.jmdev.app.imagify.domain.repository

import com.jmdev.app.imagify.utils.PhotoQuality

interface UserPreferencesRepository {
    suspend fun getPhotoQuality(): PhotoQuality
    suspend fun getSearchOrientation(): String
    suspend fun saveQuality(quality: PhotoQuality)
    suspend fun saveSearchOrientation(orientation: String)
}
