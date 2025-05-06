package com.bondidos.auth.presentation.profile.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class ProfileState(
    val isLoading: Boolean,
) : Reducer.ViewState {

    companion object {
        fun init() = ProfileState(
            isLoading = false,
        )

    }
}