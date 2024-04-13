package com.jmdev.app.imagify.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.photo.Photo
import com.jmdev.app.imagify.ui.navigation.core.LocalNavigationController
import com.jmdev.app.imagify.ui.navigation.core.NavigationRoutes
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun PhotoCard(modifier: Modifier = Modifier, appViewModel: AppViewModel, photo: Photo) {
    val navController = LocalNavigationController.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                appViewModel.setPhoto(photo)
                navController.navigate(NavigationRoutes.ImageDetail.route) {
                    launchSingleTop = true
                }
            }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            CoilImageComponent(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clip(
                        RoundedCornerShape(dimensionResource(id = R.dimen.default_clip))
                    ),
                data = photo,
                viewModel = appViewModel
            )
            PhotoPrevData(data = photo)
        }
    }
}