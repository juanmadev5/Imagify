package com.jmdev.app.imagify.ui.screens.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.components.PhotoCard
import com.jmdev.app.imagify.ui.theme.MainColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Search(
    modifier: Modifier = Modifier,
    scaffoldPaddingValues: PaddingValues,
    navigateToDetail: () -> Unit,
    searchPhotos: LazyPagingItems<Photo>,
    searchPhotosLazyState: LazyListState,
    scope: CoroutineScope,
    state: MutableState<Boolean>
) {
    if (searchPhotos.itemCount == 0) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search),
                tint = MainColor,
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .size(40.dp)
            )
            Text(text = "Search images")
        }
    } else {
        if (searchPhotos.loadState.refresh is LoadState.Loading) {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(dimensionResource(id = R.dimen.circular_indicator_size))
                        .align(Alignment.Center)
                )
            }
        } else {
            LaunchedEffect(Unit) {
                if (state.value) {
                    scope.launch {
                        searchPhotosLazyState.animateScrollToItem(0)
                        state.value = false
                    }
                }
            }
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = scaffoldPaddingValues.calculateTopPadding())
                    .navigationBarsPadding()
                    .fillMaxSize(),
                state = searchPhotosLazyState,
                userScrollEnabled = true
            ) {
                item {
                    Spacer(modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_normal)))
                }
                items(searchPhotos.itemCount) { photo ->
                    searchPhotos[photo]?.let {
                        PhotoCard(
                            photo = it
                        ) {
                            navigateToDetail()
                        }
                    }
                }

                searchPhotos.apply {
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
                                Box(modifier = modifier.fillMaxSize()) {
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
}