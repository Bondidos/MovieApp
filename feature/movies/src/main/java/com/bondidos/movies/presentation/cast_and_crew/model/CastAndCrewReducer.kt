package com.bondidos.movies.presentation.cast_and_crew.model

import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewEffect
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewEvent
import com.bondidos.movies.presentation.cast_and_crew.intent.CastAndCrewState
import com.bondidos.movies.presentation.movie_details_screen.intent.CrewAndCastUI
import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class CastAndCrewReducer @Inject constructor() :
    Reducer<CastAndCrewState, CastAndCrewEvent, CastAndCrewEffect> {
    override fun reduce(
        previousState: CastAndCrewState,
        event: CastAndCrewEvent
    ): Pair<CastAndCrewState, CastAndCrewEffect?> {
        return when (event) {
            CastAndCrewEvent.Loading -> previousState.copy(isLoading = true) to null
            CastAndCrewEvent.Loaded -> previousState.copy(isLoading = false) to null
            is CastAndCrewEvent.CrewAndCastLoaded -> previousState.copy(
                crewAndCast = event.cast.map {
                    CrewAndCastUI(
                        it.imageUrl,
                        it.person?.name ?: "",
                        it.activities?.firstOrNull() ?: ""
                    )
                }
            ) to null

            is CastAndCrewEvent.HandleError -> previousState to CastAndCrewEffect.ShowErrorMessage(
                event.message
            )
        }
    }
}