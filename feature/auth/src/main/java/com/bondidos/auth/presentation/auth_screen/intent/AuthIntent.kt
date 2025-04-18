package com.bondidos.auth.presentation.auth_screen.intent

import androidx.credentials.GetCredentialResponse
import com.bondidos.ui.base_mvi.Intention

sealed class AuthIntent : Intention {
    data object Login : AuthIntent()
    data object SignIn : AuthIntent()
    data class LoginWithGoogle(val response: GetCredentialResponse?) : AuthIntent()
    data class EmailChanged(val value: String) : AuthIntent()
    data class PasswordChanged(val value: String) : AuthIntent()
}