package com.jmdev.app.imagify.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.jmdev.app.imagify.R

@Composable
fun NavigationBar(modifier: Modifier = Modifier, navIndex: MutableIntState) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.padding_12)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.nav_bar_clip)))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_12),
                    vertical = dimensionResource(id = R.dimen.padding6),
                ),
        ) {
            NavItem(
                label = stringResource(R.string.home),
                icon = Icons.Filled.Home,
                selected = navIndex.intValue == 0,
                onClick = { navIndex.intValue = 0 },
            )
            NavItem(
                label = stringResource(R.string.search),
                icon = Icons.Filled.Search,
                selected = navIndex.intValue == 1,
                onClick = { navIndex.intValue = 1 },
            )
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.nav_item_clip)))
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(id = R.dimen.nav_item_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.padding_normal),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_extra_small)),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)))
                .background(
                    if (selected) MaterialTheme.colorScheme.secondaryContainer
                    else Color.Transparent
                )
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_12),
                    vertical = dimensionResource(id = R.dimen.spacing_extra_small),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(dimensionResource(id = R.dimen.nav_item_icon_size)),
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
        )
    }
}
