package com.bondidos.movies.presentation.movie_details_screen.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.ui.theme.colors.AppThemeColor
import com.bondidos.core_ui.theme.composables.MoviesAppbar
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEffect
import com.bondidos.movies.presentation.movie_details_screen.model.MovieDetailsScreenViewModel
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsState
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppScreen

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(viewModel.effect, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                is MovieDetailsEffect.ShowErrorMessage -> TODO()
            }
        }
    }

    AppScreen(isLoading = state.value.isLoading) {
        MovieDetailsScreenContent(
            viewModel = viewModel,
            state = state,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Composable
fun MovieDetailsScreenContent(
    viewModel: MovieDetailsScreenViewModel,
    state: State<MovieDetailsState>,
    snackBarHostState: SnackbarHostState,
) {

    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_movies,
                titleTextStyle = MaterialTheme.typography.titleLarge,
//                afterLeadingTitle = R.string.title_sign_out,
//                onAfterLeadingClick = { viewModel.emitIntent(MoviesIntent.SingOut) },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        Text("DETAILS")
    }
}
