package com.bondidos.feature_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bondidos.core_navigation_impl.AppNavigator
import com.bondidos.core_navigation_impl.AppScreen
import com.bondidos.core_ui.theme.colors.AppThemeColor
import com.bondidos.core_ui.theme.MovieAppTheme

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.background(
            AppThemeColor.APP_BACKGROUND.color()
        ),
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(text = "AuthScreen")
            ElevatedButton(
                onClick = {
                    viewModel.navigateToMovies()
                },
            ) {
                Text("Navigate to MoviesFeature")
            }
        }
    }
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

