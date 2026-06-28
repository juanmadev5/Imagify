package com.jmdev.app.imagify.presentation.screens.imageDetailScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.components.AuthorComponent
import com.jmdev.app.imagify.presentation.components.DetailImageComponent
import com.jmdev.app.imagify.presentation.components.ImageDetailTopBar
import com.jmdev.app.imagify.presentation.components.NetworkErrorContent
import com.jmdev.app.imagify.presentation.components.PhotoMetadataItem
import com.jmdev.app.imagify.presentation.theme.topBarColors
import com.jmdev.app.imagify.utils.PhotoQuality
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ImageDetail(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    photoId: String,
    quality: PhotoQuality,
) {
    val context: Context = LocalContext.current
    val viewModel: ImageDetailViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val downloadStatus by viewModel.downloadStatus.collectAsStateWithLifecycle()

    LaunchedEffect(photoId) {
        viewModel.loadPhoto(photoId)
    }

    when (val state = uiState) {
        is ImageDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ImageDetailUiState.Error -> {
            Scaffold(
                modifier = modifier.safeDrawingPadding().fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.photo_detail)) },
                        colors = topBarColors(),
                        navigationIcon = {
                            IconButton(onClick = navigateToHome) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.go_back_),
                                )
                            }
                        },
                    )
                },
            ) { innerPadding ->
                NetworkErrorContent(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    isNetworkError = state.isNetworkError,
                    onRetry = { viewModel.loadPhoto(photoId) },
                )
            }
        }

        is ImageDetailUiState.Success -> {
            val photo = state.photo
            val photoUrl = when (quality) {
                PhotoQuality.RAW -> photo.urls.raw
                PhotoQuality.FULL -> photo.urls.full
                PhotoQuality.REGULAR -> photo.urls.regular
            }

            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            val notAvailable = stringResource(R.string.not_available)

            val locationText = buildString {
                photo.location?.city?.takeIf { it.isNotBlank() }?.let { append(it) }
                photo.location?.country?.takeIf { it.isNotBlank() }?.let {
                    if (isNotEmpty()) append(", ")
                    append(it)
                }
            }.ifEmpty { notAvailable }

            val photoDataList = listOf(
                stringResource(R.string.created_at) to (photo.createdAt?.dropLast(10) ?: notAvailable),
                stringResource(R.string.likes_data) to "${photo.likes}",
                stringResource(R.string.resolution) to "${photo.width}x${photo.height}",
                stringResource(R.string.location) to locationText,
            )

            val cameraList = listOfNotNull(
                photo.exif?.name?.let { stringResource(R.string.camera_name) to it },
                photo.exif?.exposureTime?.let { stringResource(R.string.exposure_time) to it },
                photo.exif?.aperture?.let { stringResource(R.string.aperture) to it },
                photo.exif?.focalLength?.let { stringResource(R.string.focal_lenght) to it },
                photo.exif?.iso?.let { stringResource(R.string.iso) to "$it" },
            )

            Scaffold(
                modifier = modifier
                    .safeDrawingPadding()
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    ImageDetailTopBar(
                        navigateToHome = navigateToHome,
                        url = photo.links.html,
                        scrollBehavior = scrollBehavior,
                    )
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            viewModel.onEvent(
                                ImageDetailUiEvent.DownloadPhoto(
                                    context = context,
                                    link = photo.urls.full,
                                    fileName = "${photo.user.username}_${photo.user.name}_${photo.createdAt ?: photo.id}",
                                )
                            )
                        },
                        icon = {
                            when (downloadStatus) {
                                DownloadStatus.LOADING -> CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                )
                                DownloadStatus.SUCCESS -> Icon(Icons.Filled.Done, null)
                                DownloadStatus.ERROR -> Icon(Icons.Filled.Warning, null)
                                DownloadStatus.IDLE -> Icon(
                                    painter = painterResource(R.drawable.ic_download_photo),
                                    contentDescription = null,
                                )
                            }
                        },
                        text = {
                            Text(
                                stringResource(
                                    when (downloadStatus) {
                                        DownloadStatus.LOADING -> R.string.downloading_photo
                                        DownloadStatus.SUCCESS -> R.string.saved
                                        DownloadStatus.ERROR -> R.string.download_failed
                                        DownloadStatus.IDLE -> R.string.download
                                    }
                                )
                            )
                        },
                    )
                },
            ) { innerPadding ->
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(bottom = 88.dp),
                ) {
                    // Hero image
                    item {
                        DetailImageComponent(
                            data = photo,
                            url = photoUrl,
                        )
                    }

                    // Author card
                    item {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        ) {
                            AuthorComponent(data = state.userProfile)
                        }
                    }

                    // Description card (only when available)
                    if (!photo.description.isNullOrBlank()) {
                        item {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = stringResource(R.string.description),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        text = photo.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                        }
                    }

                    // Photo info card
                    item {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Image,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp),
                                    )
                                    Text(
                                        text = stringResource(R.string.photo_data),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                                photoDataList.forEachIndexed { index, (label, value) ->
                                    PhotoMetadataItem(label = label, value = value)
                                    if (index < photoDataList.lastIndex) {
                                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                                    }
                                }
                                // Color row with visual indicator
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                                PhotoMetadataItem(
                                    label = stringResource(R.string.color),
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    runCatching {
                                                        Color(android.graphics.Color.parseColor(photo.color))
                                                    }.getOrDefault(Color.Transparent)
                                                )
                                        )
                                        Text(
                                            text = photo.color,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }
                                Spacer(Modifier.height(4.dp))
                            }
                        }
                    }

                    // Camera card (only when at least one exif field is present)
                    if (cameraList.isNotEmpty()) {
                        item {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.CameraAlt,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp),
                                        )
                                        Text(
                                            text = stringResource(R.string.camera_data),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    }
                                    cameraList.forEachIndexed { index, (label, value) ->
                                        PhotoMetadataItem(label = label, value = value)
                                        if (index < cameraList.lastIndex) {
                                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                                        }
                                    }
                                    Spacer(Modifier.height(4.dp))
                                }
                            }
                        }
                    }

                    // Tags card (only when tags exist)
                    if (photo.tags.isNotEmpty()) {
                        item {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = stringResource(R.string.tags),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                    ) {
                                        photo.tags.forEach { tag ->
                                            SuggestionChip(
                                                onClick = {},
                                                label = { Text(tag.title) },
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
