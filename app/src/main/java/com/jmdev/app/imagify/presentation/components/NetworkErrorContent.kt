package com.jmdev.app.imagify.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.jmdev.app.imagify.R

@Composable
fun NetworkErrorContent(
    modifier: Modifier = Modifier,
    isNetworkError: Boolean,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.network_error_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = if (isNetworkError) Icons.Filled.WifiOff else Icons.Filled.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(dimensionResource(id = R.dimen.network_error_icon_size)),
        )
        Spacer(Modifier.height(dimensionResource(id = R.dimen.padding_large)))
        Text(
            text = if (isNetworkError)
                stringResource(R.string.no_internet_connection)
            else
                stringResource(R.string.error_getting_image),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(dimensionResource(id = R.dimen.padding_large)))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}
