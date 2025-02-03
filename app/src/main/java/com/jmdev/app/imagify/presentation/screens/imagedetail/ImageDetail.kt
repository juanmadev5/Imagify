package com.jmdev.app.imagify.presentation.screens.imagedetail

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.components.ImageDetailTopBar
import com.jmdev.app.imagify.presentation.viewmodel.getVM
import com.jmdev.app.imagify.utils.PhotoQuality
import com.jmdev.app.imagify.utils.coilImageBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetail(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navigateToHome: () -> Unit,
    permissionRequest: () -> Unit,
    photoId: String,
    photoQuality: String,
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

    var loading by remember { mutableStateOf(false) }

    val imageDetailViewModel: ImageDetailViewModel = getVM()
    val q = PhotoQuality.valueOf(photoQuality)
    LaunchedEffect(Unit) {
        loading = true
        imageDetailViewModel.setPermissionGranted()
        imageDetailViewModel.getPhoto(photoId)
        imageDetailViewModel.getQuality(q)
        loading = false
    }

    val photo by imageDetailViewModel.photo.collectAsStateWithLifecycle()
    val quality by imageDetailViewModel.photoQuality.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    if(!loading) {
        if (photo != null) {
            Scaffold(
                modifier = modifier
                    .safeDrawingPadding()
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    ImageDetailTopBar(
                        context = context,
                        navigateToHome = { navigateToHome() },
                        permissionRequest = { permissionRequest() },
                        data = photo!!,
                        scrollBehavior = scrollBehavior
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    val photoDataList = listOf(
                        Pair(
                            stringResource(R.string.created_at), photo?.createdAt?.dropLast(10) ?: ""
                        ),
                        Pair(
                            stringResource(R.string.likes_data), "${photo?.likes ?: ""}"
                        ),
                        Pair(
                            stringResource(R.string.resolution), "${photo?.width ?: ""}x${photo?.height ?: ""}"
                        ),
                        Pair(
                            stringResource(R.string.color), photo?.color  ?: ""
                        )
                    )

                    val photoUrl = when (quality) {
                        PhotoQuality.RAW -> photo?.urls?.raw
                        PhotoQuality.FULL -> photo?.urls?.full
                        PhotoQuality.REGULAR -> photo?.urls?.regular
                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        SubcomposeAsyncImage(
                            model = coilImageBuilder(
                                data = photoUrl!!,
                                cacheKey = photoUrl
                            ),
                            contentDescription = photo!!.description
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
                    Text(
                        text = stringResource(R.string.description),
                        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = photo!!.description ?: stringResource(R.string.no_description_available),
                        modifier = modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_large),
                            end = dimensionResource(id = R.dimen.padding_large)
                        )
                    )
                    Text(
                        text = stringResource(R.string.photo_data),
                        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    photoDataList.forEach {
                        photoDataList(data = it)
                    }
                }
            }

        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }


}