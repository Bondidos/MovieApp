package com.bondidos.auth.auth_screen.intent

import com.bondidos.ui.base_mvi.Intention

sealed class AuthIntent : Intention {
    data object Login : AuthIntent()
    data object SignIn: AuthIntent()
    data object LoginWithGoogle: AuthIntent()
    data class EmailChanged(val value: String): AuthIntent()
    data class PasswordChanged(val value: String): AuthIntent()
}