package com.jmdev.app.imagify.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.utils.shareUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(
    modifier: Modifier = Modifier,
    context: Context,
    navigateToHome: () -> Unit,
    url: String,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.photo_detail),
                fontSize = dimensionResource(id = R.dimen.title).value.sp,
                fontWeight = FontWeight(900),
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier.statusBarsPadding(),
        colors = TopAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        navigationIcon = {
            IconButton(
                onClick = { navigateToHome() }, modifier = modifier
                    .statusBarsPadding()
                    .padding(dimensionResource(id = R.dimen.padding_normal))
                    .size(dimensionResource(id = R.dimen.img_detail_arrow_back_size))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back_),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    shareUrl(context, url)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(
                        R.string.download
                    ),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}