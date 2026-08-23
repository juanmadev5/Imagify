package com.jmdev.app.imagify.presentation.screens.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.components.NetworkErrorContent
import com.jmdev.app.imagify.presentation.components.PhotoCard
import com.jmdev.app.imagify.presentation.components.SearchTextField
import com.jmdev.app.imagify.presentation.utils.photoGridColumnCount
import org.koin.androidx.compose.koinViewModel
import java.io.IOException

@Composable
fun Search(
    modifier: Modifier = Modifier,
    scaffoldPaddingValues: PaddingValues,
    navigateToDetail: (String, String) -> Unit,
    searchPhotosLazyState: LazyStaggeredGridState,
) {
    val viewModel: MainScreenViewModel = koinViewModel()
    val searchPhotos = viewModel.searchPhotos.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(searchPhotos.loadState.refresh) {
        if (searchPhotos.loadState.refresh is LoadState.Loading) {
            searchPhotosLazyState.scrollToItem(0)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = scaffoldPaddingValues.calculateTopPadding()),
    ) {
        SearchTextField(
            searchQuery = uiState.searchQuery,
            onQueryCommit = { viewModel.onEvent(MainScreenUiEvent.QueryPhotos(it)) },
        )

        when {
            searchPhotos.loadState.refresh is LoadState.Loading -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.circular_indicator_size))
                            .align(Alignment.Center)
                    )
                }
            }

            searchPhotos.loadState.refresh is LoadState.Error && searchPhotos.itemCount == 0 -> {
                val error = (searchPhotos.loadState.refresh as LoadState.Error).error
                NetworkErrorContent(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    isNetworkError = error is IOException,
                    onRetry = { searchPhotos.retry() },
                )
            }

            searchPhotos.itemCount == 0 -> {
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.search_empty_icon_size)),
                    )
                    Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_12)))
                    Text(
                        text = stringResource(R.string.search),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            else -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(photoGridColumnCount()),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    state = searchPhotosLazyState,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_normal)),
                    verticalItemSpacing = dimensionResource(id = R.dimen.padding_normal),
                ) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Spacer(Modifier.padding(top = dimensionResource(id = R.dimen.padding_normal)))
                    }
                    items(
                        count = searchPhotos.itemCount,
                        key = searchPhotos.itemKey { it.id },
                    ) { index ->
                        searchPhotos[index]?.let { photo ->
                            PhotoCard(
                                feedPhoto = photo,
                                photoQuality = uiState.photoQuality,
                                navigateToDetail = navigateToDetail,
                            )
                        }
                    }
                    searchPhotos.apply {
                        when (loadState.append) {
                            is LoadState.Loading -> {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .size(dimensionResource(id = R.dimen.circular_indicator_size))
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                            }
                            is LoadState.Error -> {
                                val error = (loadState.append as LoadState.Error).error
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    NetworkErrorContent(
                                        modifier = Modifier.fillMaxWidth(),
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
}
