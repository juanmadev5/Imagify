package com.jmdev.app.imagify.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.presentation.theme.topBarColors
import com.jmdev.app.imagify.utils.shareUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    url: String,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val context = LocalContext.current
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = stringResource(R.string.photo_detail))
        },
        colors = topBarColors(),
        navigationIcon = {
            IconButton(onClick = navigateToHome) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.go_back_),
                )
            }
        },
        actions = {
            IconButton(onClick = { shareUrl(context, url) }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(R.string.share_photo),
                )
            }
        },
    )
}
