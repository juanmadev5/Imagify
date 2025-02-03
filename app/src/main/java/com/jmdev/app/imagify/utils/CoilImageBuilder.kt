package com.jmdev.app.imagify.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.jmdev.app.imagify.App
import kotlinx.coroutines.Dispatchers

@Composable
fun coilImageBuilder(data: String, cacheKey: String): ImageRequest {
    return ImageRequest.Builder(LocalContext.current)
        .data(data)
        .decoderDispatcher(Dispatchers.IO)
        .crossfade(true)
        .crossfade(App.CROSSFADE_VALUE)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCacheKey(cacheKey)
        .diskCacheKey(cacheKey)
        .build()
}