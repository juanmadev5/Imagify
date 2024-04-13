package com.jmdev.app.imagify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.photo.Photo
import com.jmdev.app.imagify.ui.components.core.AuthorComponent

@Composable
fun PhotoPrevData(modifier: Modifier = Modifier, data: Photo) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(id = R.dimen.padding_12))
    ) {
        AuthorComponent(data = data)
        Row(
            modifier = modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_12),
                    bottom = dimensionResource(id = R.dimen.padding_normal)
                )
                .wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(R.string.likes_alt)
            )
            Text(
                text = stringResource(R.string.likes, data.likes ?: "0"),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_normal))
            )
        }
        Row(
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_normal),
                bottom = dimensionResource(id = R.dimen.padding_normal),
                end = dimensionResource(id = R.dimen.padding_normal)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val parsedColor = android.graphics.Color.parseColor(data.color)
            Box(
                modifier = modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_normal))
                    .size(dimensionResource(id = R.dimen.color_box_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.color_box_cut)))
                    .background(Color(parsedColor))
            )
            Text(
                text = data.color ?: "No color",
                modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_normal))
            )
        }
    }
}