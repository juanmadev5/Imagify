package com.jmdev.app.imagify.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.jmdev.app.imagify.R
import com.jmdev.app.imagify.ui.theme.MainColor

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    navItemList: List<NavItems>,
    navIndex: MutableIntState
) {
    Row(
        modifier = modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_normal))
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = modifier.weight(1f))
        Row(
            modifier = modifier
                .width(150.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            navItemList.forEach {
                Box(
                    modifier = modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_normal),
                            bottom = dimensionResource(id = R.dimen.padding_normal)
                        )
                        .size(dimensionResource(id = R.dimen.navBarItemSize))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_clip)))
                        .background(if (navIndex.intValue == it.index) MainColor else MaterialTheme.colorScheme.background)
                        .clickable {
                            navIndex.intValue = it.index
                        }
                ) {
                    Icon(
                        modifier = modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        imageVector = it.icon,
                        contentDescription = it.title,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Spacer(modifier = modifier.weight(1f))
    }
}