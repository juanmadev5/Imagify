package com.jmdev.app.imagify.presentation.screens.settings

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.DownloadQualityModel
import com.jmdev.app.imagify.model.OrientationModel
import com.jmdev.app.imagify.model.QualityModel
import com.jmdev.app.imagify.presentation.components.SelectorComponent
import com.jmdev.app.imagify.presentation.screens.mainscreen.MainScreenViewModel
import com.jmdev.app.imagify.presentation.viewmodel.getVM
import com.jmdev.app.imagify.utils.PhotoQuality

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
) {
    val mainScreenViewModel: MainScreenViewModel = getVM()
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

    val orientationList = listOf(
        OrientationModel(
            orientation = R.string.orientation_portrait,
            orientationToApply = App.ORIENTATION_PORTRAIT
        ),
        OrientationModel(
            orientation = R.string.orientation_landscape,
            orientationToApply = App.ORIENTATION_LANDSCAPE
        ),
        OrientationModel(
            orientation = R.string.orientation_squarish,
            orientationToApply = App.ORIENTATION_SQUARISH
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
                onClick = { navigateToHome() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.go_back_),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = stringResource(id = R.string.settings),
                fontSize = dimensionResource(id = R.dimen.title).value.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary
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
                checked = mainScreenViewModel.photoQuality.collectAsStateWithLifecycle().value == quality.qualityToApply,
                enabled = true
            ) {
                mainScreenViewModel.setQuality(quality = quality.qualityToApply)
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
                checked = mainScreenViewModel.downloadQuality.collectAsStateWithLifecycle().value == dQuality.downloadQuality,
                enabled = true
            ) {
                mainScreenViewModel.setDownloadQuality(dQuality.downloadQuality)
            }
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
                checked = mainScreenViewModel.searchPhotoOrientation.collectAsStateWithLifecycle().value == orientation.orientationToApply,
                enabled = true
            ) {
                mainScreenViewModel.setSearchOrientation(orientation = orientation.orientationToApply)
            }
        }
    }
}