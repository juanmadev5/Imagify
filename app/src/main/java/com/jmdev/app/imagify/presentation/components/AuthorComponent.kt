package com.jmdev.app.imagify.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.size
import com.jmdev.app.imagify.model.photo.User
import com.jmdev.app.imagify.utils.buildImageRequest

@Composable
fun AuthorComponent(modifier: Modifier = Modifier, data: User?) {
    val context = LocalContext.current
    val avatarUrl = data?.profileImage?.medium
    val request = remember(avatarUrl) {
        avatarUrl?.let { buildImageRequest(context, it, it) }
    }
    val personIcon = rememberVectorPainter(Icons.Filled.Person)

    ListItem(
        modifier = modifier,
        leadingContent = {
            AsyncImage(
                model = request,
                contentDescription = null,
                placeholder = personIcon,
                error = personIcon,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
            )
        },
        headlineContent = {
            Text(
                text = data?.name ?: "",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        supportingContent = {
            Text(
                text = "@${data?.username ?: ""}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}
