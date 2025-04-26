package com.bondidos.movies.presentation.movie_details_screen.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.ui.theme.colors.AppThemeColor
import com.bondidos.ui.composables.MoviesAppbar
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEffect
import com.bondidos.movies.presentation.movie_details_screen.model.MovieDetailsScreenViewModel
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsState
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.MovieStarRow
import com.bondidos.ui.composables.NetworkImage
import com.bondidos.ui.theme.MovieAppTheme
import com.bondidos.ui.theme.appColors

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
    val data = state.value
    val scroll = rememberScrollState()

    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
//        topBar = {
//            MoviesAppbar(
        //todo back button
//                titleRes = R.string.title_movies,
//                titleTextStyle = MaterialTheme.typography.titleLarge,
//                afterLeadingTitle = R.string.title_sign_out,
//                onAfterLeadingClick = { viewModel.emitIntent(MoviesIntent.SingOut) },
//            )
//        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        if (!data.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(scroll, Orientation.Vertical),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.height(385.dp)) {
                    NetworkImage(
                        url = data.image,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .height(260.dp)
                            .fillMaxWidth()
                            .alpha(0.2f)
                            .align(Alignment.TopCenter)
                    )
                    NetworkImage(
                        url = data.image,
                        modifier = Modifier
                            .size(165.dp, 250.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
                Spacer(Modifier.padding(bottom = 30.dp))
                Text(
                    data.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.appColors.mainText
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    data.durationAndCertification,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.appColors.disabledText,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    data.genres,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.appColors.disabledText,
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        data.rating,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.appColors.mainText
                    )
                    Spacer(Modifier.width(15.dp))
                    MovieStarRow(data.stars, true)
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL, showBackground = true)
@Composable
fun ImagePreview() {
    MovieAppTheme {
//        NetworkImage(
//            url = "https://img.omdbapi.com/?apikey=a8c50fd3&i=tt14123284",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier.height(260.dp)
//        )
//        MovieDetailsScreen()
    }
}
