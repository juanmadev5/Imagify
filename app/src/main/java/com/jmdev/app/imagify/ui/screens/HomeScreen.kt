package com.jmdev.app.imagify.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jmdev.app.imagify.App
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.core.State
import com.jmdev.app.imagify.ui.components.PhotoCard
import com.jmdev.app.imagify.ui.navigation.core.LocalNavigationController
import com.jmdev.app.imagify.ui.navigation.core.NavigationRoutes
import com.jmdev.app.imagify.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, appViewModel: AppViewModel) {
    @Suppress("DEPRECATION") val uiController = rememberSystemUiController()
    uiController.setStatusBarColor(MaterialTheme.colorScheme.surface)
    val navController = LocalNavigationController.current
    val photos = appViewModel.photos.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val topBarColor = TopAppBarColors(
        scrolledContainerColor = MaterialTheme.colorScheme.background,
        containerColor = MaterialTheme.colorScheme.background,
        actionIconContentColor = MaterialTheme.colorScheme.primary,
        navigationIconContentColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
    val currentIndex by appViewModel.currentIndex.collectAsState()


    if (appViewModel.state.collectAsState().value != State.ERROR) {
        if (photos == null) {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(dimensionResource(id = R.dimen.circular_indicator_size))
                        .align(Alignment.Center)
                )
            }
        } else {
            Scaffold(
                modifier = modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(dimensionResource(id = R.dimen.padding_12)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    fontSize = 20.sp
                                )
                                Row {
                                    IconButton(
                                        onClick = {
                                            appViewModel.fetchPhotos()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Refresh,
                                            contentDescription = stringResource(
                                                R.string.refresh
                                            )
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            navController.navigate(
                                                NavigationRoutes.Settings.route
                                            ) {
                                                launchSingleTop = true
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Settings,
                                            contentDescription = stringResource(id = R.string.settings)
                                        )
                                    }
                                }
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        colors = topBarColor
                    )
                }
            ) {
                AnimatedVisibility(
                    visible = photos.isNotEmpty(),
                    enter = fadeIn(tween(durationMillis = App.HOME_ANIMATION_SPEC)),
                    exit = fadeOut(tween(durationMillis = App.HOME_ANIMATION_SPEC))
                ) {
                    LazyColumn(
                        modifier = modifier
                            .padding(it)
                            .fillMaxSize(),
                        state = rememberLazyListState(initialFirstVisibleItemIndex = currentIndex)
                    ) {
                        items(photos.size) { ph ->
                            PhotoCard(
                                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_normal)),
                                appViewModel = appViewModel,
                                photo = photos[ph]!!
                            )
                        }
                    }
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.try_again),
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Button(onClick = { /*TODO*/ }, modifier = modifier.align(Alignment.BottomCenter)) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.retry)
                )
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}