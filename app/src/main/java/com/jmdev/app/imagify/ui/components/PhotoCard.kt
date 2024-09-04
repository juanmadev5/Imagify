package com.jmdev.app.imagify.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.viewmodel.ImagifyViewModel
import com.jmdev.app.imagify.ui.viewmodel.getVM

@Composable
fun PhotoCard(
    modifier: Modifier = Modifier,
    photo: Photo,
    navigateToDetail: () -> Unit
) {
    val imagifyViewModel: ImagifyViewModel = getVM()
    Card(
        modifier = modifier
            .wrapContentHeight()
            .padding(dimensionResource(id = R.dimen.padding_normal))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)))
            .clickable {
                imagifyViewModel.setPhoto(photo)
                navigateToDetail()
            }
    ) {
        AuthorComponent(
            modifier = modifier.padding(
                top = dimensionResource(
                    id = R.dimen.padding6
                ), start = dimensionResource(
                    id = R.dimen.padding6
                ), end = dimensionResource(
                    id = R.dimen.padding6
                )
            ), data = photo
        )
        ImageComponent(
            modifier = modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)))
                .align(Alignment.CenterHorizontally),
            data = photo
        )
    }
}