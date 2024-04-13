package com.jmdev.app.imagify.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.core.GetPhotosMode
import com.jmdev.app.imagify.core.Orientation
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.model.DownloadQualityModel
import com.jmdev.app.imagify.model.GetPhotosModeModel
import com.jmdev.app.imagify.model.OrderModel
import com.jmdev.app.imagify.model.OrientationModel
import com.jmdev.app.imagify.model.QualityModel
import com.jmdev.app.imagify.ui.components.SelectorComponent
import com.jmdev.app.imagify.ui.navigation.core.LocalNavigationController
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    navController: NavController = LocalNavigationController.current,
    appViewModel: AppViewModel
) {
    val qualityList = listOf(
        QualityModel(
            quality = R.string.raw,
            qualityToApply = PhotoQuality.RAW
        ),
        QualityModel(
            quality = R.string.full,
            qualityToApply = PhotoQuality.FULL
        ),
        QualityModel(
            quality = R.string.regular,
            qualityToApply = PhotoQuality.REGULAR
        )
    )
    val orientationList = listOf(
        OrientationModel(
            orientation = R.string.orientation_portrait,
            orientationToApply = Orientation.PORTRAIT
        ),
        OrientationModel(
            orientation = R.string.orientation_landscape,
            orientationToApply = Orientation.LANDSCAPE
        ),
        OrientationModel(
            orientation = R.string.squarish,
            orientationToApply = Orientation.SQUARISH
        )
    )

    val orderList = listOf(
        OrderModel(
            order = R.string.latest,
            orderByToApply = App.ORDER_BY_LATEST
        ),
        OrderModel(
            order = R.string.oldest,
            orderByToApply = App.ORDER_BY_OLDEST
        ),
        OrderModel(
            order = R.string.popular,
            orderByToApply = App.ORDER_BY_POPULAR
        )
    )

    val downloadQuality = listOf(
        DownloadQualityModel(
            quality = R.string.raw,
            downloadQuality = PhotoQuality.RAW
        ),
        DownloadQualityModel(
            quality = R.string.full,
            downloadQuality = PhotoQuality.FULL
        ),
        DownloadQualityModel(
            quality = R.string.regular,
            downloadQuality = PhotoQuality.REGULAR
        )
    )

    val getPhotosMode = listOf(
        GetPhotosModeModel(
            mode = R.string.editorial_feed,
            getPhotosMode = GetPhotosMode.EDITORIAL_FEED
        ),
        GetPhotosModeModel(
            mode = R.string.random,
            getPhotosMode = GetPhotosMode.RANDOM
        )
    )

    Column(
        modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_normal))
                .fillMaxWidth()
                .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.go_back_)
                )
            }
            Text(
                text = stringResource(id = R.string.settings),
                fontSize = dimensionResource(id = R.dimen.settings_title).value.sp
            )
        }
        Text(
            text = stringResource(R.string.photo_quality),
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_normal),
                top = dimensionResource(id = R.dimen.padding_large)
            ),
            fontSize = dimensionResource(id = R.dimen.settings_items_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        qualityList.forEach { quality ->
            SelectorComponent(
                item = quality.quality,
                checked = appViewModel.photoQuality.collectAsState().value == quality.qualityToApply,
                enabled = true
            ) {
                appViewModel.setQuality(quality = quality.qualityToApply)
                appViewModel.fetchPhotos()
            }
        }
        Text(
            text = stringResource(R.string.order_by),
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_normal),
                top = dimensionResource(id = R.dimen.padding_large)
            ),
            fontSize = dimensionResource(id = R.dimen.settings_items_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        orderList.forEach { order ->
            SelectorComponent(
                item = order.order,
                checked = appViewModel.orderBy.collectAsState().value == order.orderByToApply,
                enabled = appViewModel.getPhotosMode.collectAsState().value == GetPhotosMode.EDITORIAL_FEED
            ) {
                appViewModel.setOrder(order.orderByToApply)
                appViewModel.fetchPhotos()
            }
        }
        Text(
            text = stringResource(R.string.download_quality),
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_normal),
                top = dimensionResource(id = R.dimen.padding_large)
            ),
            fontSize = dimensionResource(id = R.dimen.settings_items_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        downloadQuality.forEach { dQuality ->
            SelectorComponent(
                item = dQuality.quality,
                checked = appViewModel.downloadQuality.collectAsState().value == dQuality.downloadQuality,
                enabled = true
            ) {
                appViewModel.setDownloadQuality(dQuality.downloadQuality)
            }
        }
        Text(
            text = stringResource(R.string.get_photos_mode),
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_normal),
                top = dimensionResource(id = R.dimen.padding_large)
            ),
            fontSize = dimensionResource(id = R.dimen.settings_items_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        getPhotosMode.forEach { mode ->
            SelectorComponent(
                item = mode.mode,
                checked = appViewModel.getPhotosMode.collectAsState().value == mode.getPhotosMode,
                enabled = true
            ) {
                appViewModel.setGetPhotosMode(mode.getPhotosMode)
            }
        }
        Text(
            text = stringResource(R.string.photo_orientation_home),
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_normal),
                top = dimensionResource(id = R.dimen.padding_large)
            ),
            fontSize = dimensionResource(id = R.dimen.settings_items_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        orientationList.forEach { orientation ->
            SelectorComponent(
                item = orientation.orientation,
                checked = appViewModel.homePhotoOrientation.collectAsState().value == orientation.orientationToApply,
                enabled = appViewModel.getPhotosMode.collectAsState().value == GetPhotosMode.RANDOM
            ) { appViewModel.setHomeOrientation(orientation = orientation.orientationToApply) }
        }

        /*
        Text(
            text = stringResource(R.string.photo_orientation_search),
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                bottom = dimensionResource(id = R.dimen.padding_normal),
                top = dimensionResource(id = R.dimen.padding_large)
            ),
            fontSize = dimensionResource(id = R.dimen.settings_items_text_size).value.sp,
            fontWeight = FontWeight.Bold
        )
        orientationList.forEach { orientation ->
            SelectorComponent(
                item = orientation.orientation,
                checked = appViewModel.searchPhotoOrientation.collectAsState().value == orientation.orientationToApply
            ) { appViewModel.setSearchOrientation(orientation = orientation.orientationToApply) }
        }*/

    }
}