package com.jmdev.app.imagify.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.presentation.screens.mainScreen.MainScreenViewModel
import com.jmdev.app.imagify.utils.BlurHashDecoder
import com.jmdev.app.imagify.utils.PhotoQuality
import com.jmdev.app.imagify.utils.coilImageBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedImageComponent(
    modifier: Modifier = Modifier,
    data: FeedPhoto,
    navToDetail: (String) -> Unit = {}
) {
    val viewModel: MainScreenViewModel = koinViewModel()
    val quality by viewModel.photoQuality.collectAsStateWithLifecycle()
    val photoUrl = when (quality) {
        PhotoQuality.RAW -> data.urls.raw
        PhotoQuality.FULL -> data.urls.full
        PhotoQuality.REGULAR -> data.urls.regular
    }

    val imageRatio = data.width?.toFloat()?.div(data.height ?: 1) ?: (800f / 600f)

    val placeholderSize = 64
    val placeholderHeight = (placeholderSize / imageRatio).toInt()

    val placeHolderBitmap by produceState<Bitmap?>(initialValue = null) {
        value = withContext(Dispatchers.IO) {
            BlurHashDecoder.decode(
                blurHash = data.blurHash,
                width = placeholderSize,
                height = placeholderHeight
            )
        }
    }

    SubcomposeAsyncImage(
        model = coilImageBuilder(
            data = photoUrl,
            cacheKey = photoUrl
        ),
        contentScale = ContentScale.Fit,
        contentDescription = data.description,
        modifier = modifier
            .padding(top = dimensionResource(id = R.dimen.padding_normal))
            .aspectRatio(imageRatio)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)))
            .clickable {
                navToDetail(photoUrl)
            },
        loading = {
            placeHolderBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)))
                )
            } ?: Box(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_normal))
                    .aspectRatio(imageRatio)
            )
        }
    )
}