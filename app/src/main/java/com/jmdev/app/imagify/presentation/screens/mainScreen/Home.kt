package com.jmdev.app.imagify.presentation.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.components.NetworkErrorContent
import com.jmdev.app.imagify.presentation.components.PhotoCard
import org.koin.androidx.compose.koinViewModel
import java.io.IOException

@Composable
fun Home(
    navigateToDetail: (String, String) -> Unit,
    modifier: Modifier,
    scaffoldPaddingValues: PaddingValues,
) {
    val viewModel: MainScreenViewModel = koinViewModel()
    val homePhotos = viewModel.homePhotos.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        homePhotos.loadState.refresh is LoadState.Loading -> {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(dimensionResource(id = R.dimen.circular_indicator_size))
                        .align(Alignment.Center)
                )
            }
        }

        homePhotos.loadState.refresh is LoadState.Error && homePhotos.itemCount == 0 -> {
            val error = (homePhotos.loadState.refresh as LoadState.Error).error
            NetworkErrorContent(
                modifier = modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddingValues),
                isNetworkError = error is IOException,
                onRetry = { homePhotos.retry() },
            )
        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .padding(top = scaffoldPaddingValues.calculateTopPadding())
                    .navigationBarsPadding()
                    .fillMaxSize(),
                userScrollEnabled = true
            ) {
                items(
                    count = homePhotos.itemCount,
                    key = homePhotos.itemKey { it.id }
                ) { index ->
                    homePhotos[index]?.let { photo ->
                        PhotoCard(
                            feedPhoto = photo,
                            photoQuality = uiState.photoQuality,
                            navigateToDetail = navigateToDetail
                        )
                    }
                }
                homePhotos.apply {
                    when (loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(modifier = modifier.fillMaxSize()) {
                                    CircularProgressIndicator(
                                        modifier = modifier
                                            .size(dimensionResource(id = R.dimen.circular_indicator_size))
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                        is LoadState.Error -> {
                            val error = (loadState.append as LoadState.Error).error
                            item {
                                NetworkErrorContent(
                                    modifier = modifier.fillMaxSize(),
                                    isNetworkError = error is IOException,
                                    onRetry = { retry() },
                                )
                            }
                        }
                        is LoadState.NotLoading -> {}
                    }
                }
            }
        }
    }
}
