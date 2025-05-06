package com.bondidos.auth.presentation.profile.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class ProfileEvent : Reducer.ViewEvent {
    data object Loading : ProfileEvent()
}
