package com.bondidos.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bondidos.ui.theme.MovieAppTheme
import com.bondidos.ui.theme.appColors
import com.bondidos.ui.R
import com.bondidos.ui.theme.colors.AppThemeColor

sealed class MovieType(
    val index: Int,
) {
    data object Trending : MovieType(1)
    data object Anticipated : MovieType(2)

    companion object {
        fun fromString(value: String?): MovieType {
            return when (value) {
                Trending.toString() -> Trending
                Anticipated.toString() -> Anticipated
                else -> throw IllegalArgumentException()
            }
        }
    }
}

@Composable
fun MoviesTabRow(
    onChange: (MovieType) -> Unit,
    currentMovieType: MovieType,
) {
    TabRow(
        selectedTabIndex = currentMovieType.index,
        indicator = { },
        divider = { },
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        contentColor = AppThemeColor.APP_BACKGROUND.color(),
        modifier = Modifier.border(
            width = 1.dp,
            shape = RoundedCornerShape(16.dp),
            color = AppThemeColor.BORDER_COLOR.color()
        )
    ) {
        AppTab(
            type = MovieType.Trending,
            selected = currentMovieType == MovieType.Trending,
            onClick = { onChange(it) },
            stringResId = R.string.title_trending
        )

        AppTab(
            type = MovieType.Anticipated,
            selected = currentMovieType == MovieType.Anticipated,
            onClick = { onChange(it) },
            stringResId = R.string.title_anticipated
        )
    }
}

@Composable
private fun AppTab(
    type: MovieType,
    selected: Boolean,
    onClick: (MovieType) -> Unit,
    stringResId: Int
) {
    val tabColor =
        if (selected) AppThemeColor.ACTIVE_BUTTON.color() else AppThemeColor.APP_BACKGROUND.color()
    val textColor =
        if (selected) MaterialTheme.appColors.onButtonText else MaterialTheme.appColors.disabledText

    Tab(
        selected = selected,
        onClick = { onClick(type) },
        text = {
            Surface(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth(),
                color = tabColor,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(stringResId),
                        style = MaterialTheme.typography.titleSmall,
                        color = textColor
                    )
                }
            }
        }

    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMovies() {
    val type = remember { mutableStateOf<MovieType>(MovieType.Trending) }

    MovieAppTheme {
        MoviesTabRow(
            currentMovieType = type.value,
            onChange = { type.value = it }
        )
    }
}