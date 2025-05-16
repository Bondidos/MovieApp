package com.bondidos.movies.presentation.cast_and_crew.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Intention

@Immutable
sealed class CastAndCrewIntent : Intention {
    data object GoBack : CastAndCrewIntent()
}