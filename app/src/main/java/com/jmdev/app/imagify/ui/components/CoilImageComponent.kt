package com.jmdev.app.imagify.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.components.core.coilImageBuilder
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun CoilImageComponent(modifier: Modifier = Modifier, data: Photo, viewModel: AppViewModel) {
    val photoQuality = when (viewModel.photoQuality.collectAsState().value) {
        PhotoQuality.RAW -> data.urls.raw
        PhotoQuality.FULL -> data.urls.full
        PhotoQuality.REGULAR -> data.urls.regular
    }
    AsyncImage(
        model = coilImageBuilder(
            data = photoQuality,
            cacheKey = data.id
        ),
        contentDescription = data.description,
        modifier = modifier.fillMaxWidth()
    )
}