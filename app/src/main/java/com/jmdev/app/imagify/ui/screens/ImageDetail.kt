package com.jmdev.app.imagify.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.core.State
import com.jmdev.app.imagify.ui.components.core.coilImageBuilder
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@Composable
fun ImageDetail(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel(),
    context: Context = LocalContext.current,
    imageId: String
) {
    @Composable
    fun photoDataList(data: Pair<String, String>) {
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

    @Composable
    fun cameraDataList(data: Pair<String, String>) {
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

    val data = appViewModel.getPhoto(imageId)
    val brush = Brush.verticalGradient(
        listOf(
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 51),
            Color(0, 0, 0, 102),
            Color(0, 0, 0, 153)
        )
    )
    if (appViewModel.getPhotoState.collectAsState().value == State.OK) {
        val photoDataList = listOf(
            Pair(
                stringResource(R.string.author), data!!.user.name
            ),
            Pair(
                stringResource(R.string.created_at), data.createdAt
            ),
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
                stringResource(R.string.make), data.exif.make
            ),
            Pair(
                stringResource(R.string.model), data.exif.model
            ),
            Pair(
                stringResource(R.string.exposure_time), data.exif.exposureTime
            ),
            Pair(
                stringResource(R.string.aperture), data.exif.aperture
            ),
            Pair(
                stringResource(R.string.focal_lenght), data.exif.focalLength
            ),
            Pair(
                stringResource(R.string.iso), "${data.exif.iso}"
            )
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                AsyncImage(
                    model = coilImageBuilder(
                        data = imageId,
                        cacheKey = imageId
                    ),
                    contentDescription = imageId,
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                IconButton(
                    onClick = { /*TODO*/ }, modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_normal))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.img_detail_arrow_back_clip)))
                        .background(MaterialTheme.colorScheme.background)
                        .align(Alignment.TopStart)
                        .size(dimensionResource(id = R.dimen.img_detail_arrow_back_size))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.go_back_)
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .background(brush)
                        .padding(dimensionResource(id = R.dimen.padding_large)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = stringResource(R.string.location),
                            tint = Color.White
                        )
                        Text(
                            text = data.location.country,
                            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_normal)),
                            color = Color.White
                        )
                    }
                    IconButton(onClick = {
                        appViewModel.downloadPhoto(
                            context,
                            data.urls.raw,
                            "${data.id}_${data.description}_${data.user.username}"
                        )
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = stringResource(
                                R.string.download
                            ),
                            tint = Color.White
                        )
                    }
                }
            }
            if (data.description != null) {
                Text(
                    text = stringResource(R.string.description, data.description),
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large))
                )
            } else {
                Text(
                    text = stringResource(R.string.description_no_available),
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large))
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
            Text(
                text = stringResource(R.string.camera_data),
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                fontWeight = FontWeight.Bold
            )
            cameraDataList.forEach {
                cameraDataList(data = it)
            }
        }
    } else if (appViewModel.getPhotoState.collectAsState().value == State.WAITING) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.loading))
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.error_getting_image))
        }
    }
}