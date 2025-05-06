package com.bondidos.movies.presentation.cast_and_crew.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewEffect
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewIntent
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewState
import com.bondidos.movies.presentation.cast_and_crew.model.CrewAndCastScreenViewModel
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.MoviesAppbar
import com.bondidos.ui.composables.PersonCard
import com.bondidos.ui.theme.colors.AppThemeColor

@Composable
fun CastAndCrewScreen(
    viewModel: CrewAndCastScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                is CastAndCrewEffect.ShowErrorMessage -> snackBarHostState.showSnackbar(
                    action.message
                )
            }
        }
    }

    AppScreen(isLoading = state.value.isLoading) {
        CastAndCrewScreenContent(
            viewModel,
            state,
            snackBarHostState
        )
    }
}

@Composable
fun CastAndCrewScreenContent(
    viewModel: CrewAndCastScreenViewModel,
    state: State<CastAndCrewState>,
    snackBarHostState: SnackbarHostState,
) {
    val data = state.value
    val scroll = rememberScrollState()

    Scaffold(
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_cast_and_crew,
                titleTextStyle = MaterialTheme.typography.titleLarge,
                leadingIconRes = R.drawable.back_arrow,
                onLeadingClick = { viewModel.emitIntent(CastAndCrewIntent.GoBack) }
            )
        },
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp, vertical = 20.dp)
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            data.crewAndCast.forEach {
                PersonCard(it.imageUrl, it.personName, it.role)
                Spacer(modifier = Modifier.height(20.dp))
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}