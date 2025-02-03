package com.jmdev.app.imagify.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import coil.compose.SubcomposeAsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.utils.BlurHashDecoder
import com.jmdev.app.imagify.utils.coilImageBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DetailImageComponent(
    modifier: Modifier = Modifier,
    data: Photo,
    url: String,
) {

    val imageRatio = data.width.toFloat().div(data.height)

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
            data = url,
            cacheKey = url
        ),
        contentScale = ContentScale.Fit,
        contentDescription = data.description,
        modifier = modifier
            .padding(top = dimensionResource(id = R.dimen.padding_normal))
            .aspectRatio(imageRatio)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip))),
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
