package com.jmdev.app.imagify.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.core.GetPhotosMode
import com.jmdev.app.imagify.core.PhotoQuality
import com.jmdev.app.imagify.ui.components.core.AuthorComponent
import com.jmdev.app.imagify.ui.components.core.coilImageBuilder
import com.jmdev.app.imagify.ui.navigation.core.LocalNavigationController
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetail(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    context: Context = LocalContext.current,
    navController: NavController = LocalNavigationController.current
) {
    @Composable
    fun photoDataList(data: Pair<String, String?>?) {
        if (data != null) {
            Text(
                text = data.first + data.second,
                modifier = modifier.padding(
                    top = dimensionResource(id = R.dimen.padding_normal),
                    start = dimensionResource(id = R.dimen.padding_large),
                    end = dimensionResource(id = R.dimen.padding_large),
                    bottom = dimensionResource(id = R.dimen.padding_normal)
                )
            )
        }
    }

    val data = appViewModel.photo.collectAsState().value

    if (data != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            val photoDataList = listOf(
                data.createdAt?.let {
                    Pair(
                        stringResource(R.string.created_at), it.dropLast(10)
                    )
                },
                Pair(
                    stringResource(R.string.likes_data), "${data.likes}"
                ),
                Pair(
                    stringResource(R.string.resolution), "${data.width}x${data.height}"
                ),
                Pair(
                    stringResource(R.string.color), data.color
                )
            )
            val cameraDataList = listOf(
                Pair(
                    stringResource(id = R.string.make), "${data.exif?.make}"
                ),
                Pair(
                    stringResource(id = R.string.model), "${data.exif?.model}"
                ),
                Pair(
                    stringResource(id = R.string.exposure_time), "${data.exif?.exposureTime}"
                ),
                Pair(
                    stringResource(id = R.string.aperture), "${data.exif?.aperture}"
                ),
                Pair(
                    stringResource(id = R.string.focal_lenght), "${data.exif?.focalLength}"
                ),
                Pair(
                    stringResource(id = R.string.iso), "${data.exif?.iso}"
                )
            )
            val topBarColor = TopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                containerColor = MaterialTheme.colorScheme.background,
                actionIconContentColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            )
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.photo_detail))
                },
                modifier = modifier.statusBarsPadding(),
                colors = topBarColor,
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }, modifier = modifier
                            .statusBarsPadding()
                            .padding(dimensionResource(id = R.dimen.padding_normal))
                            .size(dimensionResource(id = R.dimen.img_detail_arrow_back_size))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back_)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val quality = when (appViewModel.downloadQuality.value) {
                                PhotoQuality.RAW -> data.urls.raw
                                PhotoQuality.FULL -> data.urls.full
                                PhotoQuality.REGULAR -> data.urls.regular
                            }
                            appViewModel.downloadPhoto(
                                context,
                                quality,
                                "${data.user?.username}_${data.user?.name}_${data.createdAt}"
                            )
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_download_photo),
                            contentDescription = stringResource(
                                R.string.download
                            ),
                            tint = Color.White
                        )
                    }
                }
            )
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                SubcomposeAsyncImage(
                    model = coilImageBuilder(
                        data = appViewModel.getQuality()!!,
                        cacheKey = data.id
                    ),
                    contentDescription = data.description
                        ?: stringResource(R.string.no_description),
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(id = R.dimen.home_photocard_default_size))
                        )
                    }
                )
            }
            AuthorComponent(
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_normal)),
                data = data
            )
            if (data.description != null) {
                Text(
                    text = stringResource(R.string.description, data.description),
                    modifier = modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_large),
                        end = dimensionResource(id = R.dimen.padding_large),
                        bottom = dimensionResource(id = R.dimen.padding_large),
                        top = dimensionResource(id = R.dimen.padding_normal)
                    )
                )
            }
            Text(
                text = stringResource(R.string.photo_data),
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                fontWeight = FontWeight.Bold
            )
            photoDataList.forEach {
                photoDataList(data = it)
            }
            if (appViewModel.getPhotosMode.collectAsState().value == GetPhotosMode.RANDOM) {
                Text(
                    text = stringResource(R.string.camera_data),
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                    fontWeight = FontWeight.Bold
                )
                cameraDataList.forEach {
                    photoDataList(data = it)
                }
            }
        }
    }
}