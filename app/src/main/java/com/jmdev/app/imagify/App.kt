package com.jmdev.app.imagify

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {
    companion object {
        const val API_KEY = "vjfuubS-Th7vYIb2R7tAsA5D0rguJJQf4tQt3rq00BY"
        const val BASE_URL = "https://api.unsplash.com/"
        const val DEFAULT_PER_PAGE = 20
        const val DEFAULT_MAX_QUANTITY = 30
        const val CROSSFADE_VALUE = 900
        const val ANIMATION_SPEC = 500
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