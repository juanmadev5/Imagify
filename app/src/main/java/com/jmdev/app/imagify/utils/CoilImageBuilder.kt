package com.jmdev.app.imagify.utils

import android.content.Context
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.jmdev.app.imagify.CROSSFADE_VALUE

fun buildImageRequest(context: Context, data: String, cacheKey: String): ImageRequest =
    ImageRequest.Builder(context)
        .data(data)
        .crossfade(CROSSFADE_VALUE)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCacheKey(cacheKey)
        .diskCacheKey(cacheKey)
        .build()
