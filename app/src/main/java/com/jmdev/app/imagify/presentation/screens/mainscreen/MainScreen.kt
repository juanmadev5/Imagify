package com.jmdev.app.imagify.presentation.screens.mainscreen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.model.NavItems
import com.jmdev.app.imagify.presentation.components.HomeTopBar
import com.jmdev.app.imagify.presentation.components.NavigationBar
import com.jmdev.app.imagify.presentation.components.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    navigateToDetail: (String, String) -> Unit,
) {
    val searchLazyState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val state = rememberSaveable { mutableStateOf(false) }
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
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .nestedScroll(navBarScrollBehavior.nestedScrollConnection),
        topBar = {
            if (navIndex.intValue == 0) {
                HomeTopBar(
                    navigateToSettings = { navigateToSettings() },
                    scrollBehavior = scrollBehavior
                )
            } else {
                SearchTextField(
                    state = state,
                    scrollBehavior = scrollBehavior,
                    searchLazyState = searchLazyState,
                )
            }
        },
        bottomBar = {
            NavigationBar(
                navItemList = navItemList,
                navIndex = navIndex,
                scrollBehavior = navBarScrollBehavior
            )
        }
    ) {
        Crossfade(targetState = navIndex.intValue) { currentScreen ->
            when (currentScreen) {
                0 -> {
                    Home(
                        navigateToDetail = { photoId, url ->
                            navigateToDetail(
                                photoId,
                                url
                            )
                        },
                        modifier = modifier,
                        scaffoldPaddingValues = it
                    )
                }

                1 -> {
                    Search(
                        scaffoldPaddingValues = it,
                        navigateToDetail = { photoId, url ->
                            navigateToDetail(
                                photoId,
                                url
                            )
                        },
                        searchPhotosLazyState = searchLazyState,
                        state = state
                    )
                }
            }
        }
    }
}