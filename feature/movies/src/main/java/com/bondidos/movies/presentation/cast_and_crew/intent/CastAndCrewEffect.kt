package com.bondidos.movies.presentation.cast_and_crew.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class CastAndCrewEffect : Reducer.ViewEffect {
    data class ShowErrorMessage(val message: String) : CastAndCrewEffect()
}