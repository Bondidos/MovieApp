package com.bondidos.auth.presentation.sing_up_screen.intent

import com.bondidos.ui.base_mvi.Intention

sealed class SingUpIntent : Intention {
    data object CreateAccount : SingUpIntent()
    data class EmailChanged(val value: String) : SingUpIntent()
    data class PasswordChanged(val value: String) : SingUpIntent()
    data class PasswordRetypedChanged(val value: String) : SingUpIntent()
}