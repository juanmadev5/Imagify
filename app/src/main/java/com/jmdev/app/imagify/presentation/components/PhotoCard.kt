package com.jmdev.app.imagify.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jmdev.app.imagify.model.photo.FeedPhoto

@Composable
fun PhotoCard(
    feedPhoto: FeedPhoto,
    navigateToDetail: (String, String) -> Unit,
) {
    FeedImageComponent(
        modifier = Modifier
            .clickable {

            },
        data = feedPhoto,
        navToDetail = { url ->
            navigateToDetail(feedPhoto.id, url)
        }
    )
}