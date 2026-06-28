package com.jmdev.app.imagify.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.utils.BlurHashDecoder
import com.jmdev.app.imagify.utils.buildImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DetailImageComponent(
    modifier: Modifier = Modifier,
    data: Photo,
    url: String,
) {
    val imageRatio = data.width.toFloat() / data.height
    val placeholderSize = 64
    val placeholderHeight = (placeholderSize / imageRatio).toInt()

    val colorPlaceholder = remember(data.color) {
        runCatching { ColorPainter(Color(android.graphics.Color.parseColor(data.color))) }.getOrNull()
    }

    val blurHashBitmap by produceState<Bitmap?>(initialValue = null, data.blurHash) {
        value = withContext(Dispatchers.IO) {
            BlurHashDecoder.decode(data.blurHash, placeholderSize, placeholderHeight)
        }
    }

    val placeholder = remember(blurHashBitmap) {
        blurHashBitmap?.let { BitmapPainter(it.asImageBitmap()) } ?: colorPlaceholder
    }

    val context = LocalContext.current
    val request = remember(url) { buildImageRequest(context, url, url) }

    AsyncImage(
        model = request,
        contentDescription = data.description,
        contentScale = ContentScale.Fit,
        placeholder = placeholder,
        modifier = modifier
            .padding(top = dimensionResource(id = R.dimen.padding_normal))
            .aspectRatio(imageRatio)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip))),
    )
}
