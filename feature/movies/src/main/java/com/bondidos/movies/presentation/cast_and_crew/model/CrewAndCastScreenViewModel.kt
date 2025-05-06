package com.bondidos.movies.presentation.cast_and_crew.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.base.UseCaseResult
import com.bondidos.movies.domain.model.people.PeopleWithImage
import com.bondidos.movies.domain.usecase.GetCrewAndCastUseCase
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewEffect
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewEvent
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewIntent
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrewAndCastScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    private val getCrewAndCast: GetCrewAndCastUseCase,
    reducer: CastAndCrewReducer,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CastAndCrewState, CastAndCrewEvent, CastAndCrewEffect>(
    CastAndCrewState.init(),
    reducer
) {
    init {
        appAnalytics.logScreen(ScreenNames.CastAndCrew)

        val movieId: Int? = savedStateHandle[MOVIE_ID_KEY]

        viewModelScope.launch(Dispatchers.IO) {
            movieId?.let {
                getCrewAndCast(it)
                    .onStart { reduce(CastAndCrewEvent.Loading) }
                    .collect(::handleOnCrewReceived)
            }
        }
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            CastAndCrewIntent.GoBack -> {
                appAnalytics.logButton(ButtonNames.GoBack)

                appNavigator.pop()
            }
        }
    }

    private fun handleOnCrewReceived(
        value: UseCaseResult<List<PeopleWithImage>>
    ) {
        when (value) {
            is UseCaseResult.Success -> reduce(
                CastAndCrewEvent.CrewAndCastLoaded(
                    value.data
                )
            )

            is UseCaseResult.Error -> reduce(
                CastAndCrewEvent.HandleError(
                    value.error.message ?: "Unknown Error"
                )
            )
        }

        reduce(CastAndCrewEvent.Loaded)
    }

    companion object {
        const val MOVIE_ID_KEY = "movieId"
    }
}
