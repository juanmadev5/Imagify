package com.jmdev.app.imagify.ui.screens.imagedetail

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.ui.components.AuthorComponent
import com.jmdev.app.imagify.ui.components.coilImageBuilder
import com.jmdev.app.imagify.ui.theme.MainColor
import com.jmdev.app.imagify.ui.viewmodel.ImagifyViewModel
import com.jmdev.app.imagify.ui.viewmodel.getVM

@Composable
fun ImageDetail(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navigateToHome: () -> Unit,
    permissionRequest: () -> Unit,
) {
    val imagifyViewModel: ImagifyViewModel = getVM()

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

    val data = imagifyViewModel.photo.collectAsStateWithLifecycle().value

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

            ImageDetailTopBar(
                imagifyViewModel = imagifyViewModel,
                context = context,
                navigateToHome = { navigateToHome() },
                permissionRequest = { permissionRequest() },
                data = data
            )

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                SubcomposeAsyncImage(
                    model = coilImageBuilder(
                        data = imagifyViewModel.getQuality()!!,
                        cacheKey = data.id
                    ),
                    contentDescription = data.description
                        ?: stringResource(R.string.no_description),
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip))),
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
                modifier = modifier.padding(
                    top = dimensionResource(id = R.dimen.padding_12),
                    start = dimensionResource(id = R.dimen.padding_normal),
                    end = dimensionResource(id = R.dimen.padding_normal)
                ),
                data = data
            )
            Text(
                text = stringResource(R.string.description),
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                fontWeight = FontWeight.Bold,
                color = MainColor
            )
            Text(
                text = data.description ?: stringResource(R.string.no_description_available),
                modifier = modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_large),
                    end = dimensionResource(id = R.dimen.padding_large)
                )
            )
            Text(
                text = stringResource(R.string.photo_data),
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                fontWeight = FontWeight.Bold,
                color = MainColor
            )
            photoDataList.forEach {
                photoDataList(data = it)
            }
        }
    }
}