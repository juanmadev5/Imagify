package com.jmdev.app.imagify.presentation.screens.imagedetail

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.components.DetailImageComponent
import com.jmdev.app.imagify.presentation.components.ImageDetailTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetail(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navigateToHome: () -> Unit,
    permissionRequest: () -> Unit,
    photoId: String,
    url: String,
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

    val imageDetailViewModel: ImageDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        loading = true
        imageDetailViewModel.setPermissionGranted()
        imageDetailViewModel.getPhoto(photoId)
        loading = false
    }

    val photo by imageDetailViewModel.photo.collectAsStateWithLifecycle(null)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    if (!loading && photo != null) {
        Scaffold(
            modifier = modifier
                .safeDrawingPadding()
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                ImageDetailTopBar(
                    context = context,
                    navigateToHome = { navigateToHome() },
                    url = photo!!.links.html,
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    val granted = imageDetailViewModel.isPermissionGranted.value
                    if (!granted) {
                        permissionRequest()
                    } else {
                        imageDetailViewModel.downloadPhoto(
                            context,
                            photo!!.links.download,
                            "${photo!!.user.username}_${photo!!.user.name}_${photo!!.createdAt}"
                        )
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_download_photo),
                        contentDescription = stringResource(
                            R.string.download
                        ),
                    )
                }
            }
        ) { innerPadding ->
            val photoDataList = listOf(
                Pair(
                    stringResource(R.string.created_at),
                    photo?.createdAt?.dropLast(10) ?: "Not available"
                ),
                Pair(
                    stringResource(R.string.likes_data), "${photo?.likes ?: ""}"
                ),
                Pair(
                    stringResource(R.string.resolution),
                    "${photo?.width ?: ""}x${photo?.height ?: ""}"
                ),
                Pair(
                    stringResource(R.string.color), photo?.color ?: ""
                ),
                Pair(
                    "Location: ",
                    "${photo!!.location?.city ?: "Not available"} | ${photo!!.location?.country ?: ""}"
                )
            )

            val camera = listOf(
                Pair(
                    stringResource(R.string.camera_name),
                    photo!!.exif?.name ?: "Not available"
                ),
                Pair(
                    stringResource(R.string.exposure_time),
                    photo!!.exif?.exposureTime ?: "Not available"
                ),
                Pair(
                    stringResource(R.string.aperture),
                    photo!!.exif?.aperture ?: "Not available"
                ),
                Pair(
                    stringResource(R.string.focal_lenght),
                    photo!!.exif?.focalLength ?: "Not available"
                ),
                Pair(
                    stringResource(R.string.iso),
                    "${photo!!.exif?.iso ?: "Not available"}"
                )
            )
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                item {
                    DetailImageComponent(
                        data = photo!!,
                        url = Uri.decode(url)
                    )
                }
                item {
                    Row(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Person, "", modifier = Modifier.size(20.dp))
                        Text(
                            text = stringResource(R.string.author),
                            modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_normal)),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                item {
                    Text(
                        "${photo!!.user.name} | @${photo!!.user.username}",
                        modifier = modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_large),
                            end = dimensionResource(id = R.dimen.padding_large)
                        )
                    )
                }

                item {
                    VerticalDivider()
                }
                item {
                    Row(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Info, "", modifier = Modifier.size(20.dp))
                        Text(
                            text = stringResource(R.string.description),
                            modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_normal)),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                item {
                    Text(
                        text = photo!!.description
                            ?: stringResource(R.string.no_description_available),
                        modifier = modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_large),
                            end = dimensionResource(id = R.dimen.padding_large)
                        )
                    )
                }

                item {
                    VerticalDivider()
                }

                item {
                    Row(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Image, "", modifier = Modifier.size(20.dp))
                        Text(
                            text = stringResource(R.string.photo_data),
                            modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_normal)),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                items(photoDataList.size) {
                    photoDataList(data = photoDataList[it])
                }

                item {
                    VerticalDivider()
                }

                item {
                    Row(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.CameraAlt, "", modifier = Modifier.size(20.dp))
                        Text(
                            text = stringResource(R.string.camera_data),
                            modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_normal)),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                items(camera.size) {
                    photoDataList(data = camera[it])
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}