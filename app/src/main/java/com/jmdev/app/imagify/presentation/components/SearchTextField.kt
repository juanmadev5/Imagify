package com.jmdev.app.imagify.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.screens.mainScreen.MainScreenViewModel
import com.jmdev.app.imagify.presentation.theme.topBarColors
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior,
    searchLazyState: LazyListState,
) {
    val mainScreenViewModel: MainScreenViewModel = koinViewModel()
    val scope = rememberCoroutineScope()

    val query = rememberSaveable {
        mutableStateOf(mainScreenViewModel.searchQuery.value)
    }
    val nQuery = rememberSaveable {
        mutableStateOf("")
    }

    fun queryController(q: String) {
        if (query.value != q) {
            query.value = q
            mainScreenViewModel.queryPhotos(q)
            state.value = true
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    CenterAlignedTopAppBar(
        colors = topBarColors(),
        scrollBehavior = scrollBehavior,
        title = {
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip))),
                value = nQuery.value,
                onValueChange = {
                    nQuery.value = it
                },
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(
                            R.string.search_placeHolder,
                            mainScreenViewModel.searchQuery.collectAsStateWithLifecycle().value
                        )
                    )
                },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions {
                    keyboardController?.hide()
                    focusManager.clearFocus(force = true)
                    if (query.value.isNotBlank()) {
                        queryController(nQuery.value)
                    }
                    scope.launch {
                        searchLazyState.animateScrollToItem(0)
                    }
                }
            )
        }
    )
}