package com.jmdev.app.imagify.presentation.components

import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PhotoMetadataItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    headlineContent: (@Composable () -> Unit)? = null,
) {
    ListItem(
        modifier = modifier,
        overlineContent = {
            Text(
                text = label.trimEnd(':', ' '),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        headlineContent = headlineContent ?: {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}
