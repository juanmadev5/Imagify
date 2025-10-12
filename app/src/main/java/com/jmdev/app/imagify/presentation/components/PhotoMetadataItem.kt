package com.jmdev.app.imagify.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.jmdev.app.imagify.R

@Composable
fun PhotoMetadataItem(data: Pair<String, String?>?) {
    if (data != null) {
        Text(
            text = data.first + data.second,
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_normal),
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_normal)
            )
        )
    }
}