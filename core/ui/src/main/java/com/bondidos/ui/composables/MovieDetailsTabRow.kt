package com.bondidos.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bondidos.ui.R
import com.bondidos.ui.theme.colors.AppThemeColor

sealed class MovieDetailsType(
    val index: Int,
) {
    data object Detail : MovieDetailsType(1)
    data object Reviews : MovieDetailsType(2)
    data object Showtime : MovieDetailsType(3)
}

@Composable
fun MovieDetailsTabRow(
    modifier: Modifier = Modifier,
    onChange: (MovieDetailsType) -> Unit,
    currentDetailsType: MovieDetailsType,
) {
    TabRow(
        selectedTabIndex = currentDetailsType.index,
        indicator = { },
        divider = { },
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        contentColor = AppThemeColor.APP_BACKGROUND.color(),
        modifier = modifier.border(
            width = 1.dp,
            shape = RoundedCornerShape(16.dp),
            color = AppThemeColor.BORDER_COLOR.color()
        )
    ) {
        AppTab(
            type = MovieDetailsType.Detail,
            selected = currentDetailsType == MovieDetailsType.Detail,
            onClick = { onChange(it) },
            stringResId = R.string.title_trending
        )

        AppTab(
            type = MovieDetailsType.Reviews,
            selected = currentDetailsType == MovieDetailsType.Reviews,
            onClick = { onChange(it) },
            stringResId = R.string.title_anticipated
        )
        AppTab(
            type = MovieDetailsType.Showtime,
            selected = currentDetailsType == MovieDetailsType.Showtime,
            onClick = { onChange(it) },
            stringResId = R.string.title_anticipated
        )
    }
}