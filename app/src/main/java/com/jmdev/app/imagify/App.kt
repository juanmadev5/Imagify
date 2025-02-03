package com.jmdev.app.imagify

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.jmdev.app.imagify.utils.PhotoQuality
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {
    companion object {
        const val UNSPLASH_API_KEY = API_KEY
        const val BASE_URL = "https://api.unsplash.com/"
        const val DEFAULT_QUANTITY = 30
        const val ORIENTATION_PORTRAIT = "portrait"
        const val ORIENTATION_LANDSCAPE = "landscape"
        const val ORIENTATION_SQUARISH = "squarish"
        const val DEFAULT_PHOTO_ORIENTATION = "squarish"
        val DEFAULT_PHOTO_QUALITY = PhotoQuality.REGULAR
        const val CROSSFADE_VALUE = 500
        const val DS_NAME = "AppDataStore"
        val SEARCH_KEYWORDS = listOf(
            "blue sky",
            "landscape",
            "sunset",
            "mountains",
            "ocean",
            "forest",
            "cityscape",
            "wildlife",
            "flowers",
            "architecture",
            "night sky",
            "abstract",
            "portrait",
            "waterfall",
            "desert",
            "snow",
            "beach",
            "clouds",
            "stars",
            "autumn"
        )
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}