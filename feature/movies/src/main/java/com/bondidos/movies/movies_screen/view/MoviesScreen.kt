package com.bondidos.movies.movies_screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.ui.theme.colors.AppThemeColor
import com.bondidos.ui.composables.AppBottomNavBar
import com.bondidos.core_ui.theme.composables.MoviesAppbar
import com.bondidos.movies.domain.model.Movie
import com.bondidos.movies.movies_screen.intent.MoviesEffect
import com.bondidos.movies.movies_screen.intent.MoviesIntent
import com.bondidos.movies.movies_screen.intent.MoviesState
import com.bondidos.ui.R
import com.bondidos.movies.movies_screen.model.MoviesScreenViewModel
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.AppTabRow
import com.bondidos.ui.composables.MovieCard
import com.bondidos.ui.composables.MovieType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

private const val PAGINATION_COUNT = 5
private const val PAGINATION_TIMEOUT = 500L

@Composable
fun MoviesScreen(
    viewModel: MoviesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val trendingViewScrollState = rememberLazyGridState()
    val anticipatedViewScrollState = rememberLazyGridState()

    LaunchedEffect(trendingViewScrollState, anticipatedViewScrollState) {
        handleScrollState(trendingViewScrollState) { viewModel.emitIntent(MoviesIntent.NextTrendingPage) }
    }
    LaunchedEffect(anticipatedViewScrollState) {
        handleScrollState(anticipatedViewScrollState) { viewModel.emitIntent(MoviesIntent.NextAnticipatedPage) }
    }

    LaunchedEffect(viewModel.effect, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                is MoviesEffect.ShowErrorMessage -> snackBarHostState.showSnackbar(
                    action.message
                )
            }
        }
    }

    AppScreen(isLoading = state.value.isLoading) {
        MoviesScreenContent(
            viewModel = viewModel,
            state = state,
            snackBarHostState = snackBarHostState,
            trendingViewScrollState = trendingViewScrollState,
            anticipatedViewScrollState = anticipatedViewScrollState,
        )
    }
}

@Composable
fun MoviesScreenContent(
    viewModel: MoviesScreenViewModel,
    state: State<MoviesState>,
    snackBarHostState: SnackbarHostState,
    trendingViewScrollState: LazyGridState,
    anticipatedViewScrollState: LazyGridState
) {


    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_movies,
                titleTextStyle = MaterialTheme.typography.titleLarge,
                afterLeadingTitle = R.string.title_sign_out,
                onAfterLeadingClick = { viewModel.emitIntent(MoviesIntent.SingOut) },
            )
        },
        bottomBar = {
            AppBottomNavBar(
                onMovieClick = {},
                onProfileClick = {viewModel.emitIntent(MoviesIntent.NavigateToProfile)},
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
                .padding(padding)
                .padding(all = 25.dp)
        ) {
            AppTabRow(
                onChange = { viewModel.emitIntent(MoviesIntent.ToggleMovies(it)) },
                currentMovieType = state.value.moviesType
            )

            when (state.value.moviesType) {
                is MovieType.Trending -> MoviesGridView(
                    state = trendingViewScrollState,
                    movies = state.value.trendingMovies,
                )

                is MovieType.Anticipated -> MoviesGridView(
                    state = anticipatedViewScrollState,
                    movies = state.value.anticipatedMovies,
                )
            }
        }
    }
}

@Composable
fun MoviesGridView(
    movies: List<Movie>,
    state: LazyGridState,
) {
    LazyVerticalGrid(
        columns = GridCells.FixedSize(size = 164.dp),
        state = state,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(vertical = 15.dp)
    ) {
        items(movies.size) { index: Int ->
            val movie = movies[index]
            MovieCard(
                title = movie.title,
                genre = movie.genre,
                certification = movie.certification,
                image = movie.image,
                stars = movie.stars,
                duration = movie.duration,
            )
        }
    }
}

@OptIn(FlowPreview::class)
private suspend inline fun handleScrollState(
    scrollState: LazyGridState,
    crossinline handle: () -> Unit
) {
    snapshotFlow { scrollState.layoutInfo.visibleItemsInfo }
        .debounce(timeoutMillis = PAGINATION_TIMEOUT)
        .collect { visibleItems ->
            when {
                visibleItems.isNotEmpty()
                        && visibleItems.last().index
                        >= scrollState.layoutInfo.totalItemsCount - PAGINATION_COUNT ->
                    handle()
            }
        }
}