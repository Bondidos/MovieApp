package com.bondidos.feature_auth.auth_screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bondidos.core_ui.theme.MovieAppTheme
import com.bondidos.core_ui.theme.colors.AppThemeColor
import com.bondidos.core_ui.theme.composables.AppBottomNavBar
import com.bondidos.core_ui.theme.composables.AppInputTextField
import com.bondidos.core_ui.theme.composables.MoviesAppbar
import com.bondidos.core_ui.theme.composables.clickable.AppColoredButton
import com.bondidos.core_ui.theme.composables.clickable.SignWithGoogleButton
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AppScreen
import com.bondidos.ui.R

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    // TODo MainViewModel for navigation?
    val screenScrollState = rememberScrollState()

    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_profile,
                titleTextStyle = MaterialTheme.typography.titleLarge,
                afterLeadingTitle = R.string.title_sign_up,
                onAfterLeadingClick = {
                    print("clicked")
                    //Todo emit event to model
                },
            )
        },
        bottomBar = {
            AppBottomNavBar(
                onMovieClick = {},
                onProfileClick = {},
                currentItem = AppBottomNavBar.MOVIES,
            )
        },
        content = { padding ->
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(all = 25.dp)
                    .scrollable(screenScrollState, orientation = Orientation.Vertical)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AppInputTextField(
                    value = "String",
                    onValueChange = {},
                    labelTextRes = R.string.title_user_name,
                    leadingIconResId = R.drawable.user_icon,
                )
                Spacer(Modifier.height(16.dp))
                AppInputTextField(
                    value = "String",
                    onValueChange = {},
                    labelTextRes = R.string.title_password,
                    leadingIconResId = R.drawable.lock_icon,
                )
                Spacer(Modifier.height(80.dp))
                AppColoredButton(
                    onClick = { print("Clicked") },
                    titleResId = R.string.button_title_login,
                )
                Spacer(modifier = Modifier.weight(1f))
                SignWithGoogleButton(
                    onClick = { TODO() },
                )
                Spacer(Modifier.height(48.dp))
            }
        },
    )
}

@Preview(backgroundColor = 0xFFD0BCFF)
@Composable
fun AuthScreenPreview() {
    MovieAppTheme {
        AuthScreen(
            viewModel = AuthViewModel(
                appNavigator = AppNavigatorMock()
            )
        )
    }
}

class AppNavigatorMock : AppNavigator {
    override fun navigate(screen: AppScreen) {
    }
}

