package com.jmdev.app.imagify.ui.screens.mainscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.Photo
import com.jmdev.app.imagify.ui.viewmodel.ImagifyViewModel
import com.jmdev.app.imagify.ui.viewmodel.getVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    navigateToDetail: () -> Unit,
    homePhotos: LazyPagingItems<Photo>,
    lazyState: LazyListState,
    onRefresh: () -> Unit,
    shouldRefresh: MutableState<Boolean>,
    searchLazyState: LazyListState,
) {
    val imagifyViewModel: ImagifyViewModel = getVM()
    val state = rememberSaveable { mutableStateOf(false) }
    val searchPhotos = imagifyViewModel.searchPhotos.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        if (shouldRefresh.value) {
            onRefresh()
            shouldRefresh.value = false
        }
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scope = rememberCoroutineScope()
    val navIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val navItemList = listOf(
        NavItems(
            title = stringResource(R.string.home),
            icon = Icons.Filled.Home,
            index = 0
        ),
        NavItems(
            title = stringResource(R.string.search),
            icon = Icons.Filled.Search,
            index = 1
        )
    )
    Scaffold(
        modifier = modifier
            .safeDrawingPadding()
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (navIndex.intValue == 0) {
                TopBar(
                    navigateToSettings = { navigateToSettings() },
                    onRefresh = { onRefresh() },
                    scrollBehavior = scrollBehavior
                )
            } else {
                SearchField(
                    state = state,
                    navigateToSettings = { navigateToSettings() },
                    scrollBehavior = scrollBehavior,
                    searchLazyState = searchLazyState,
                    scope = scope
                )
            }
        },
        bottomBar = {
            NavigationBar(
                navItemList = navItemList,
                navIndex = navIndex
            )
        }
    ) {
        if (navIndex.intValue == 0) {
            Home(
                homePhotos = homePhotos,
                navigateToDetail = { navigateToDetail() },
                lazyState = lazyState,
                modifier = modifier,
                scaffoldPaddingValues = it
            )
        } else {
            Search(
                scaffoldPaddingValues = it,
                navigateToDetail = { navigateToDetail() },
                searchPhotos = searchPhotos,
                searchPhotosLazyState = searchLazyState,
                scope = scope,
                state = state
            )
        }
    }
}