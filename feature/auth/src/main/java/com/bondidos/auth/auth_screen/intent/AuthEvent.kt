package com.bondidos.auth.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class AuthEvent : Reducer.ViewEvent {
    data object Loading : AuthEvent()
    data class ValidationError(val emailMessageRes: Int?, val passwordMessageRes: Int?) :
        AuthEvent()
    data class EmailChanged(val value: String) : AuthEvent()
    data class PasswordChanged(val value: String) : AuthEvent()
}
