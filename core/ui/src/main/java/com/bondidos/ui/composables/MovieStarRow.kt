package com.bondidos.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.bondidos.ui.R
import com.bondidos.ui.theme.appColors

@Composable
fun MovieStarRow(
    starsCount: Int
) {
    Row {
        for (index in 1..5) {
            val iconResId =
                if (index <= starsCount) R.drawable.star_filled else R.drawable.star_empty
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = MaterialTheme.appColors.starActive
            )
        }
    }
}