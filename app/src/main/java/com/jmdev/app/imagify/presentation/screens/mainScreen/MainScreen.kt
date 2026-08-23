package com.jmdev.app.imagify.presentation.screens.mainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.components.HomeTopBar
import com.jmdev.app.imagify.presentation.components.NavigationBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    navigateToDetail: (String, String) -> Unit,
) {
    val viewModel: MainScreenViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()

    val searchLazyState = rememberLazyStaggeredGridState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val navIndex = rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (navIndex.intValue == 0) {
                HomeTopBar(
                    navigateToSettings = navigateToSettings,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        bottomBar = {
            NavigationBar(navIndex = navIndex)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Crossfade(targetState = navIndex.intValue) { currentScreen ->
                when (currentScreen) {
                    0 -> Home(
                        navigateToDetail = navigateToDetail,
                        modifier = modifier,
                        scaffoldPaddingValues = innerPadding
                    )
                    1 -> Search(
                        scaffoldPaddingValues = innerPadding,
                        navigateToDetail = navigateToDetail,
                        searchPhotosLazyState = searchLazyState,
                    )
                }
            }
            AnimatedVisibility(
                visible = !isOnline,
                modifier = Modifier.align(Alignment.TopCenter),
                enter = slideInVertically { -it } + fadeIn(),
                exit = slideOutVertically { -it } + fadeOut(),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_large),
                                vertical = dimensionResource(id = R.dimen.padding_normal),
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.WifiOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.offline_banner_icon_size)),
                        )
                        Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_normal)))
                        Text(
                            text = stringResource(R.string.no_internet_connection),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    }
                }
            }
        }
    }
}
