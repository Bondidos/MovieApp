package com.bondidos.movies.presentation.cast_and_crew.intent

import androidx.compose.runtime.Immutable
import com.bondidos.movies.presentation.movie_details_screen.intent.CrewAndCastUI
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class CastAndCrewState(
    val isLoading: Boolean,
    val crewAndCast: List<CrewAndCastUI>,
) : Reducer.ViewState {

    companion object {
        fun init() = CastAndCrewState(
            isLoading = false,
            crewAndCast = emptyList(),
        )
    }
}
