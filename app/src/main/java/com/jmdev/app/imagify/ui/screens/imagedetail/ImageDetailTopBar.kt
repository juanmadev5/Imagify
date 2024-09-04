package com.jmdev.app.imagify.ui.screens.imagedetail

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.core.shareUrl
import com.jmdev.app.imagify.ui.theme.MainColor
import com.jmdev.app.imagify.ui.viewmodel.ImagifyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(
    modifier: Modifier = Modifier,
    imagifyViewModel: ImagifyViewModel,
    context: Context,
    navigateToHome: () -> Unit,
    permissionRequest: () -> Unit,
    data: Photo
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.photo_detail),
                fontSize = dimensionResource(id = R.dimen.title).value.sp,
                fontWeight = FontWeight(900),
                fontFamily = FontFamily.Monospace,
                color = MainColor
            )
        },
        modifier = modifier.statusBarsPadding(),
        colors = TopAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        navigationIcon = {
            IconButton(
                onClick = { navigateToHome() }, modifier = modifier
                    .statusBarsPadding()
                    .padding(dimensionResource(id = R.dimen.padding_normal))
                    .size(dimensionResource(id = R.dimen.img_detail_arrow_back_size))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back_),
                    tint = MainColor
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    shareUrl(context, data.urls.raw)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(
                        R.string.download
                    ),
                    tint = MainColor
                )
            }
            IconButton(
                onClick = {
                    val granted = imagifyViewModel.isPermissionGranted.value
                    if (!granted) {
                        permissionRequest()
                    }
                    if (
                        granted
                    ) {
                        val quality = when (imagifyViewModel.downloadQuality.value) {

                            PhotoQuality.RAW -> data.urls.raw
                            PhotoQuality.FULL -> data.urls.full
                            PhotoQuality.REGULAR -> data.urls.regular
                        }
                        imagifyViewModel.downloadPhoto(
                            context,
                            quality,
                            "${data.user?.username}_${data.user?.name}_${data.createdAt}"
                        )
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download_photo),
                    contentDescription = stringResource(
                        R.string.download
                    ),
                    tint = MainColor
                )
            }
        }
    )
}