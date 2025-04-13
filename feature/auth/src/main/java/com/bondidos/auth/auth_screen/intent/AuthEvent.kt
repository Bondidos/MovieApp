package com.bondidos.auth.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class AuthEvent : Reducer.ViewEvent {
    data object Login : AuthEvent()
    data object LoginWithGoogle : AuthEvent()
    data object SingUp : AuthEvent()
    data class EmailValueChanged(val value: String) : AuthEvent()
    data class PasswordValueChanged(val value: String) : AuthEvent()
}
