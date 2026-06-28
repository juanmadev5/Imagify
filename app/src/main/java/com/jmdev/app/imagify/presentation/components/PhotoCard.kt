package com.jmdev.app.imagify.presentation.components

import androidx.compose.runtime.Composable
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.utils.PhotoQuality

@Composable
fun PhotoCard(
    feedPhoto: FeedPhoto,
    photoQuality: PhotoQuality,
    navigateToDetail: (String, String) -> Unit,
) {
    FeedImageComponent(
        data = feedPhoto,
        photoQuality = photoQuality,
        navToDetail = { url -> navigateToDetail(feedPhoto.id, url) }
    )
}
