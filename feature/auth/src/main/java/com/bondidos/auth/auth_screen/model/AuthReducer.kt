package com.bondidos.auth.auth_screen.model

import com.bondidos.auth.auth_screen.intent.AuthEffect
import com.bondidos.auth.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class AuthReducer @Inject constructor() : Reducer<AuthState, AuthEvent, AuthEffect> {
    override fun reduce(previousState: AuthState, event: AuthEvent): Pair<AuthState, AuthEffect?> {
        return when (event) {
            is AuthEvent.PasswordChanged ->
                previousState.copy(
                    passwordValue = event.value,
                    isPasswordValueError = false
                ) to null

            is AuthEvent.EmailChanged -> {
                previousState.copy(
                    emailValue = event.value,
                    isEmailError = false
                ) to null
            }

            AuthEvent.Loading -> previousState.copy(isLoading = true) to null
            is AuthEvent.EmailValidationError -> previousState.copy(isEmailError = true) to null

            is AuthEvent.PasswordValidationError -> previousState.copy(isPasswordValueError = true) to null
            is AuthEvent.ShowValidationErrorSnackBar -> previousState to AuthEffect.ValidationError(
                listOf(
                    event.email,
                    event.password
                )
            )
        }
    }
}