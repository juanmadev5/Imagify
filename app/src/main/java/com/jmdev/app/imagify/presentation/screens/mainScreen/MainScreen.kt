package com.jmdev.app.imagify.presentation.screens.mainScreen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.rememberLazyListState
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

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
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
                    searchLazyState = searchLazyState,
                )
            }
        },
        bottomBar = {
            NavigationBar(
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