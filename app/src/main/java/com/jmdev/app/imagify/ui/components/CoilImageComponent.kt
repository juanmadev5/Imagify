package com.jmdev.app.imagify.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import coil.compose.SubcomposeAsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.model.photo.Photo
import com.jmdev.app.imagify.ui.components.core.coilImageBuilder
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun CoilImageComponent(
    modifier: Modifier = Modifier,
    data: Photo,
    viewModel: AppViewModel
) {
    val photoQuality = when (viewModel.photoQuality.collectAsState().value) {
        PhotoQuality.RAW -> data.urls.raw
        PhotoQuality.FULL -> data.urls.full
        PhotoQuality.REGULAR -> data.urls.regular
    }
    SubcomposeAsyncImage(
        model = coilImageBuilder(
            data = photoQuality,
            cacheKey = data.id
        ),
        contentScale = ContentScale.Fit,
        contentDescription = data.description,
        modifier = modifier.fillMaxWidth(),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.home_photocard_default_size))
            )
        }
    )
}