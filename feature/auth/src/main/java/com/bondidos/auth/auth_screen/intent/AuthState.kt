package com.bondidos.auth.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class AuthState(
    val emailValue: String,
    val isEmailError: Boolean,
    val passwordValue: String,
    val isPasswordValueError: Boolean,
    val isLoading: Boolean,
    ) : Reducer.ViewState {

    companion object {
        fun init() = AuthState(
            emailValue = "",
            isEmailError = false,
            passwordValue = "",
            isPasswordValueError = false,
            isLoading = false,
        )
    }
}