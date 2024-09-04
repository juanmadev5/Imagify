package com.jmdev.app.imagify.ui.screens.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.components.PhotoCard

@Composable
fun Home(
    homePhotos: LazyPagingItems<Photo>,
    navigateToDetail: () -> Unit,
    lazyState: LazyListState,
    modifier: Modifier,
    scaffoldPaddingValues: PaddingValues
) {
    if (homePhotos.loadState.refresh is LoadState.Loading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = modifier
                    .size(dimensionResource(id = R.dimen.circular_indicator_size))
                    .align(Alignment.Center)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .padding(top = scaffoldPaddingValues.calculateTopPadding())
                .navigationBarsPadding()
                .fillMaxSize(),
            state = lazyState,
            userScrollEnabled = true
        ) {
            items(homePhotos.itemCount) { photo ->
                homePhotos[photo]?.let { it1 ->
                    PhotoCard(
                        photo = it1
                    ) {
                        navigateToDetail()
                    }
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
                        item {
                            Box(
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(top = dimensionResource(id = R.dimen.padding_12))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.try_again),
                                    modifier = modifier.align(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp
                                )
                            }

                        }
                    }

                    is LoadState.NotLoading -> {}
                }
            }
        }
    }
}