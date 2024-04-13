package com.jmdev.app.imagify

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.jmdev.app.imagify.core.GetPhotosMode
import com.jmdev.app.imagify.core.Orientation
import com.jmdev.app.imagify.core.PhotoQuality
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {
    companion object {
        const val API_KEY = "INCrbZ5IZVyV6uatdL5y4imGtybpINOEYtYaU_iCSH0"
        const val BASE_URL = "https://api.unsplash.com/"
        const val DEFAULT_MAX_QUANTITY = 30
        val DEFAULT_GET_PHOTOS_MODE = GetPhotosMode.EDITORIAL_FEED
        val DEFAULT_PHOTO_QUALITY = PhotoQuality.REGULAR
        val DEFAULT_PHOTO_ORIENTATION = Orientation.LANDSCAPE
        val DEFAULT_DOWNLOAD_QUALITY = PhotoQuality.RAW
        const val ORDER_BY_LATEST = "latest"
        const val ORDER_BY_OLDEST = "oldest"
        const val ORDER_BY_POPULAR = "popular"
        const val ORIENTATION_PORTRAIT = "portrait"
        const val ORIENTATION_LANDSCAPE = "landscape"
        const val ORIENTATION_SQUARISH = "squarish"
        const val CROSSFADE_VALUE = 500
        const val ANIMATION_SPEC = 900
        const val HOME_ANIMATION_SPEC = 900
        const val DS_NAME = "AppDataStore"
        private const val CACHE_DIR = "image_cache"
        private const val MAX_PERCENT_SIZE = 0.20
        private const val MAX_SIZE_PER_BYTES: Long = 5 * 1024 * 1024
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(MAX_PERCENT_SIZE)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve(CACHE_DIR))
                    .maxSizeBytes(MAX_SIZE_PER_BYTES)
                    .build()
            }
            .respectCacheHeaders(false)
            .build()
    }
}