package com.bondidos.movies.presentation.cast_and_crew.intent

import androidx.compose.runtime.Immutable
import com.bondidos.movies.domain.model.people.PeopleWithImage
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class CastAndCrewEvent : Reducer.ViewEvent {
    data object Loading : CastAndCrewEvent()
    data object Loaded : CastAndCrewEvent()
    data class CrewAndCastLoaded(val cast: List<PeopleWithImage>) : CastAndCrewEvent()
    data class HandleError(val message: String) : CastAndCrewEvent()
}
