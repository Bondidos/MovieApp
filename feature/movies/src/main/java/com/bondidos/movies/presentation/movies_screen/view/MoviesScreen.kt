package com.bondidos.movies.presentation.movies_screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.bondidos.movies.domain.model.movie.Movie
import com.bondidos.movies.presentation.movies_screen.intent.MoviesEffect
import com.bondidos.movies.presentation.movies_screen.intent.MoviesIntent
import com.bondidos.movies.presentation.movies_screen.intent.MoviesState
import com.bondidos.movies.presentation.movies_screen.model.MoviesScreenViewModel
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppBottomNavBar
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.MovieCard
import com.bondidos.ui.composables.MovieType
import com.bondidos.ui.composables.MoviesAppbar
import com.bondidos.ui.composables.MoviesTabRow
import com.bondidos.ui.theme.colors.AppThemeColor
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

private const val PAGINATION_COUNT = 5
private const val PAGINATION_TIMEOUT = 500L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val trendingViewScrollState = rememberLazyGridState()
    val anticipatedViewScrollState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(trendingViewScrollState, anticipatedViewScrollState) {
        handleScrollState(trendingViewScrollState) { viewModel.emitIntent(MoviesIntent.NextTrendingPage) }
    }
    LaunchedEffect(anticipatedViewScrollState) {
        handleScrollState(anticipatedViewScrollState) { viewModel.emitIntent(MoviesIntent.NextAnticipatedPage) }
    }

    LaunchedEffect(viewModel, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                is MoviesEffect.ShowErrorMessage -> snackBarHostState.showSnackbar(
                    action.message
                )
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = state.value.refreshing,
        onRefresh = { viewModel.emitIntent(MoviesIntent.Refresh) },
        state = pullToRefreshState,
    ) {
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
                onProfileClick = { viewModel.emitIntent(MoviesIntent.NavigateToProfile) },
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
            MoviesTabRow(
                onChange = { viewModel.emitIntent(MoviesIntent.ToggleMovies(it)) },
                currentMovieType = state.value.moviesType
            )

            when (state.value.moviesType) {
                is MovieType.Trending -> MoviesGridView(
                    state = trendingViewScrollState,
                    movies = state.value.trendingMovies,
                    onClick = { viewModel.emitIntent(MoviesIntent.ShowDetails(it)) }
                )

                is MovieType.Anticipated -> MoviesGridView(
                    state = anticipatedViewScrollState,
                    movies = state.value.anticipatedMovies,
                    onClick = { viewModel.emitIntent(MoviesIntent.ShowDetails(it)) }
                )
            }
        }
    }
}

@Composable
fun MoviesGridView(
    movies: List<Movie>,
    state: LazyGridState,
    onClick: (Int?) -> Unit
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
                modifier = Modifier.clickable { onClick(movie.id) }
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