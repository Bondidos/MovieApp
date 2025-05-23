package com.bondidos.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bondidos.ui.R
import com.bondidos.ui.theme.appColors

@Composable
fun MovieStarRow(
    starsCount: Int,
    big: Boolean = false
) {
    val size = if(big) 18.dp else 14.dp
    Row {
        for (index in 1..5) {
            val iconResId =
                if (index <= starsCount) R.drawable.star_filled else R.drawable.star_empty
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = MaterialTheme.appColors.starActive,
                modifier = Modifier.size(size)
            )
        }
    }
}