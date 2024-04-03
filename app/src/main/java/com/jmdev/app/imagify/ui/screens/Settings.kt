package com.jmdev.app.imagify.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.core.Orientation
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.model.OrientationModel
import com.jmdev.app.imagify.model.QualityModel
import com.jmdev.app.imagify.ui.components.SelectorComponent
import com.jmdev.app.imagify.ui.navigation.core.LocalNavigationController
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    navController: NavController = LocalNavigationController.current,
    appViewModel: AppViewModel = viewModel()
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

    Column(
        modifier
            .fillMaxSize()
            .statusBarsPadding()
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
                checked = appViewModel.photoQuality.collectAsState().value == quality.qualityToApply
            ) { appViewModel.setQuality(quality = quality.qualityToApply) }
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
                checked = appViewModel.homePhotoOrientation.collectAsState().value == orientation.orientationToApply
            ) { appViewModel.setHomeOrientation(orientation = orientation.orientationToApply) }
        }
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
        }
    }
}