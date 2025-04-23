package com.bondidos.movies.movies_screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.ui.theme.colors.AppThemeColor
import com.bondidos.ui.composables.AppBottomNavBar
import com.bondidos.core_ui.theme.composables.MoviesAppbar
import com.bondidos.movies.movies_screen.intent.MoviesIntent
import com.bondidos.movies.movies_screen.intent.MoviesState
import com.bondidos.ui.R
import com.bondidos.movies.movies_screen.model.MoviesScreenViewModel
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.AppTabRow
import com.bondidos.ui.composables.MovieType

@Composable
fun MoviesScreen(
    viewModel: MoviesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(viewModel, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                TODO() -> TODO()
            }
        }
    }

    AppScreen(isLoading = state.value.isLoading) {
        MoviesScreenContent(
            viewModel = viewModel,
            state = state,
            snackBarHostState = snackBarHostState
        )
    }
}

@Composable
fun MoviesScreenContent(
    viewModel: MoviesScreenViewModel,
    state: State<MoviesState>,
    snackBarHostState: SnackbarHostState
) {
    val screenScrollState = rememberScrollState()

    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_movies,
                titleTextStyle = MaterialTheme.typography.titleLarge,
            )
        },
        bottomBar = {
            AppBottomNavBar(
                //TODo Implement
                onMovieClick = {},
                onProfileClick = {},
                currentItem = AppBottomNavBar.MOVIES
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(screenScrollState)
                .padding(padding)
                .padding(all = 25.dp)
        ) {
            AppTabRow(
                onChange = { viewModel.emitIntent(MoviesIntent.ToggleMovies(it)) },
                currentMovieType = state.value.moviesType
            )
            when (state.value.moviesType) {
                is MovieType.Trending -> Text("TAB1")
                is MovieType.Anticipated -> Text("TAB2")
            }
        }
    }
}

