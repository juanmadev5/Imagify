package com.jmdev.app.imagify

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.ui.core.BlurHashDecoder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {
    companion object {
        const val API_KEY = "NA7I03lK7waSfv12CtrLLhrgWJDUH8SXAOMgs5S-uIQ"
        const val BASE_URL = "https://api.unsplash.com/"
        const val DEFAULT_QUANTITY = 30
        const val ORIENTATION_PORTRAIT = "portrait"
        const val ORIENTATION_LANDSCAPE = "landscape"
        const val ORIENTATION_SQUARISH = "squarish"
        const val DEFAULT_PHOTO_ORIENTATION = "squarish"
        val DEFAULT_PHOTO_QUALITY = PhotoQuality.REGULAR
        val DEFAULT_DOWNLOAD_QUALITY = PhotoQuality.RAW
        const val CROSSFADE_VALUE = 500
        const val DS_NAME = "AppDataStore"
        private const val CACHE_DIR = "image_cache"
        private const val MAX_PERCENT_SIZE = 0.10
        private const val MAX_SIZE_PER_BYTES: Long = 20 * 1024 * 1024
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

    override fun onLowMemory() {
        super.onLowMemory()
        BlurHashDecoder.clearCache()
    }
}