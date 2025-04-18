package com.bondidos.auth.presentation.sing_up_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class SingUpState(
    val emailValue: String,
    val isEmailError: Boolean,
    val passwordValue: String,
    val isPasswordValueError: Boolean,
    val passwordRetypedValue: String,
    val isPasswordRetypedValueError: Boolean,
    val isLoading: Boolean,
) : Reducer.ViewState {

    companion object {
        fun init() = SingUpState(
            emailValue = "",
            isEmailError = false,
            passwordValue = "",
            isPasswordValueError = false,
            passwordRetypedValue = "",
            isPasswordRetypedValueError = false,
            isLoading = false,
        )

    }
}