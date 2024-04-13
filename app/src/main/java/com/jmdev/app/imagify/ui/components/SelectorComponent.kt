package com.jmdev.app.imagify.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.jmdev.app.imagify.R

@Composable
fun SelectorComponent(
    modifier: Modifier = Modifier,
    item: Int,
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_normal),
                end = dimensionResource(id = R.dimen.padding_normal)
            )
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() },
            enabled = enabled
        )
        Text(
            text = stringResource(id = item),
            modifier = modifier.padding(
                start = dimensionResource(
                    id = R.dimen.padding_normal
                ),
                end = dimensionResource(id = R.dimen.padding_normal)
            ),
            fontSize = dimensionResource(id = R.dimen.settings_items_text_size).value.sp
        )
    }
}