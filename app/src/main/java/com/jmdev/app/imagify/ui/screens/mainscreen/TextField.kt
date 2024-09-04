package com.jmdev.app.imagify.ui.screens.mainscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.ui.theme.textFieldColors
import com.jmdev.app.imagify.ui.theme.topBarColors
import com.jmdev.app.imagify.ui.viewmodel.ImagifyViewModel
import com.jmdev.app.imagify.ui.viewmodel.getVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean>,
    navigateToSettings: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    searchLazyState: LazyListState,
    scope: CoroutineScope
) {
    val imagifyViewModel: ImagifyViewModel = getVM()
    val query = rememberSaveable {
        mutableStateOf(imagifyViewModel.query.value)
    }
    val nQuery = rememberSaveable {
        mutableStateOf("")
    }

    fun queryController(q: String) {
        if (query.value != q) {
            query.value = q
            imagifyViewModel.queryPhotos(q)
            state.value = true
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    CenterAlignedTopAppBar(
        modifier = modifier.wrapContentHeight(),
        colors = topBarColors(),
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                modifier = modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_12))
                    .fillMaxWidth()
            ) {
                Spacer(modifier = modifier.weight(0.2f))
                TextField(
                    colors = textFieldColors(),
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    modifier = modifier
                        .height(70.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)))
                        .padding(
                            bottom = dimensionResource(
                                id = R.dimen.padding_12
                            )
                        ),
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
                                imagifyViewModel.query.collectAsStateWithLifecycle().value
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
                Spacer(modifier = modifier.weight(0.2f))
                IconButton(onClick = { navigateToSettings() }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(id = R.string.settings)
                    )
                }
                Spacer(modifier = modifier.weight(0.2f))
            }
        }
    )
}