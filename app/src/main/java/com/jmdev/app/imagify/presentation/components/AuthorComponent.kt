package com.jmdev.app.imagify.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.photo.FeedPhoto
import com.jmdev.app.imagify.utils.coilImageBuilder

@Composable
fun AuthorComponent(modifier: Modifier = Modifier, data: FeedPhoto) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                bottom = dimensionResource(id = R.dimen.padding_normal),
                start = dimensionResource(id = R.dimen.padding_12),
                end = dimensionResource(id = R.dimen.padding_12)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = data.user?.profileImage?.let {
                coilImageBuilder(
                    data = it.medium,
                    cacheKey = it.medium
                )
            },
            contentDescription = data.description,
            modifier = modifier
                .size(dimensionResource(id = R.dimen.user_profile_image_size))
                .clip(
                    RoundedCornerShape(dimensionResource(id = R.dimen.user_profiel_image_cut))
                ),
            loading = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.author)
                )
            },
            success = {
                SubcomposeAsyncImageContent()
            }
        )
        Text(
            text = "${data.user?.name} | @${data.user?.username}",
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_normal)),
            fontWeight = FontWeight(600)
        )
    }
}