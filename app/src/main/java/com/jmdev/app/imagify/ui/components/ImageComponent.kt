package com.jmdev.app.imagify.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.core.BlurHashDecoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    data: Photo
) {
    val imageWidth = data.width ?: 800
    val imageHeight = data.height ?: 600

    val placeHolderBitmap by produceState<Bitmap?>(initialValue = null) {
        value = withContext(Dispatchers.IO) {
            val phW = minOf(imageWidth, 300)
            val phH = (phW * imageHeight) / imageWidth
            BlurHashDecoder.decode(
                blurHash = data.blurHash,
                width = phW,
                height = phH
            )
        }
    }

    SubcomposeAsyncImage(
        model = coilImageBuilder(
            data = data.urls.regular,
            cacheKey = data.id
        ),
        contentScale = ContentScale.Fit,
        contentDescription = data.description,
        modifier = modifier
            .aspectRatio(imageWidth.toFloat() / imageHeight.toFloat()),
        loading = {
            placeHolderBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(imageWidth.toFloat() / imageHeight.toFloat())
                )
            }
        }
    )
}
