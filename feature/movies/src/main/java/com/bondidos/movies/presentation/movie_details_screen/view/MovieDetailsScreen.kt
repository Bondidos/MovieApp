package com.bondidos.movies.presentation.movie_details_screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.movies.presentation.movie_details_screen.intent.CrewAndCastUI
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEffect
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsIntent
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsState
import com.bondidos.movies.presentation.movie_details_screen.model.MovieDetailsScreenViewModel
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.ExpandableText
import com.bondidos.ui.composables.MovieDetailsAppBar
import com.bondidos.ui.composables.MovieDetailsTabRow
import com.bondidos.ui.composables.MovieDetailsType
import com.bondidos.ui.composables.MovieStarRow
import com.bondidos.ui.composables.NetworkImage
import com.bondidos.ui.composables.PersonCard
import com.bondidos.ui.theme.appColors
import com.bondidos.ui.theme.colors.AppThemeColor

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
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        if (!data.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopPart(
                    data,
                    onBackArrowClick = { viewModel.emitIntent(MovieDetailsIntent.GoBack) },
                    onPlayClick = { viewModel.emitIntent(MovieDetailsIntent.PlayTrailer) },
                    onShareClick = { viewModel.emitIntent(MovieDetailsIntent.ShareTrailerLink) },
                    padding = padding
                )
                Spacer(Modifier.padding(bottom = 30.dp))
                ShortInfo(data)
                Spacer(modifier = Modifier.padding(40.dp))
                MovieDetailsTabRow(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    onChange = { viewModel.emitIntent(MovieDetailsIntent.MovieDetailsTypeChanged(it)) },
                    currentDetailsType = data.detailsType
                )

                when (data.detailsType) {
                    MovieDetailsType.Detail -> DetailsAndCast(
                        data.overview,
                        data.crewAndCast,
                        modifier = Modifier.padding(
                            horizontal = 18.dp,
                        ),
                        onViewAllCastTap = { viewModel.emitIntent(MovieDetailsIntent.ShowAllCastAndCrew) }
                    )

                    MovieDetailsType.Reviews -> {
                        TODO()
                    }

                    MovieDetailsType.Showtime -> {
                        TODO()
                    }
                }
            }
        }
    }
}

@Composable
private fun TopPart(
    data: MovieDetailsState,
    onBackArrowClick: () -> Unit,
    onPlayClick: () -> Unit,
    onShareClick: () -> Unit,
    padding: PaddingValues
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
        MovieDetailsAppBar(
            modifier = Modifier
                .padding(padding)
                .padding(start = 18.dp, top = 10.dp, end = 18.dp)
                .align(Alignment.TopCenter),
            onBackArrowClick = onBackArrowClick,
            onPlayClick = onPlayClick,
            onShareClick = onShareClick,
        )
    }
}

@Composable
private fun ShortInfo(
    data: MovieDetailsState,
) {
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

@Composable
private fun DetailsAndCast(
    details: String,
    cast: List<CrewAndCastUI>,
    onViewAllCastTap: () -> Unit,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        ExpandableText(
            text = details,
            minimizedMaxLines = 4,
            textStyle = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
            textColor = MaterialTheme.appColors.disabledText,
            linkTextColor = MaterialTheme.appColors.expandText,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        CastAndCrewHeader(onViewAllCastTap)
        Spacer(Modifier.height(20.dp))

        cast.take(4).forEach {
            PersonCard(it.imageUrl, it.personName, it.role)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun CastAndCrewHeader(
    onViewAllCastTap: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(com.bondidos.ui.R.string.title_cast_and_crew),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.appColors.mainText,
        )
        Text(
            stringResource(com.bondidos.ui.R.string.title_view_all),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.appColors.expandText,
            modifier = Modifier.clickable { onViewAllCastTap() }
        )
    }
}