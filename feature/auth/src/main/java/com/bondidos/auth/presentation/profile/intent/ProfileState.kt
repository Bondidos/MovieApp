package com.bondidos.auth.presentation.profile.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class ProfileState(
    val isLoading: Boolean,
    val email: String,
    val isPasswordsNotSame: Boolean,
    val oldPasswordValue: String,
    val newPasswordValue: String,
) : Reducer.ViewState {

    companion object {
        fun init() = ProfileState(
            isLoading = false,
            email = "",
            isPasswordsNotSame = false,
            oldPasswordValue = "",
            newPasswordValue = "",
        )
    }
}