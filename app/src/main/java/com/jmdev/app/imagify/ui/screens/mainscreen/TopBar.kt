package com.jmdev.app.imagify.ui.screens.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.ui.theme.MainColor
import com.jmdev.app.imagify.ui.theme.topBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    onRefresh: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        modifier = modifier
            .statusBarsPadding()
            .padding(
                start = dimensionResource(id = R.dimen.padding_12),
                end = dimensionResource(id = R.dimen.padding_12)
            ),
        title = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = dimensionResource(id = R.dimen.title).value.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    color = MainColor
                )
                Row {
                    IconButton(
                        onClick = {
                            onRefresh()
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
                            navigateToSettings()
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
        colors = topBarColors()
    )
}