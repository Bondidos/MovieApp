package com.bondidos.core_ui.theme.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bondidos.core_ui.R
import com.bondidos.core_ui.theme.MovieAppTheme
import com.bondidos.core_ui.theme.colors.AppThemeColor

enum class AppBottomNavBar {
    MOVIES, PROFILE
}

@Composable
fun AppBottomNavBar(
    onMovieClick: () -> Unit,
    onProfileClick: () -> Unit,
    currentItem: AppBottomNavBar = AppBottomNavBar.MOVIES,
) {
    val isProfileSelected = currentItem == AppBottomNavBar.PROFILE
    val isMoviesSelected = currentItem == AppBottomNavBar.MOVIES
    val colors = NavigationBarItemColors(
        selectedIconColor = AppThemeColor.NAVIGATION_ITEM_SELECTED.color(),
        selectedTextColor = Color.Transparent,
        selectedIndicatorColor = Color.Transparent,
        unselectedIconColor = AppThemeColor.NAVIGATION_BAR_ICON.color(),
        unselectedTextColor = Color.Transparent,
        disabledIconColor = Color.Transparent,
        disabledTextColor = Color.Transparent,
    )

    NavigationBar(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        modifier = Modifier.height(50.dp),
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.movies_icon),
                    contentDescription = null,
                )
            },
            selected = isMoviesSelected,
            onClick = onMovieClick,
            colors = colors,
            modifier = Modifier.size(24.dp, 24.dp)
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.profile_icon),
                    contentDescription = null,
                )
            },
            selected = isProfileSelected,
            onClick = onProfileClick,
            colors = colors,
            modifier = Modifier.size(24.dp, 24.dp)
        )
    }
}

@Preview
@Composable
fun AppBottomNavBarPreview() {
    MovieAppTheme {
        AppBottomNavBar(
            onMovieClick = {},
            onProfileClick = {},
            currentItem = AppBottomNavBar.MOVIES,
        )
    }
}