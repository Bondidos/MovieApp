package com.bondidos.auth.auth_screen.model

import android.util.Log
import com.bondidos.auth.auth_screen.intent.AuthEffect
import com.bondidos.auth.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class AuthReducer @Inject constructor() : Reducer<AuthState, AuthEvent, AuthEffect> {
    override fun reduce(previousState: AuthState, event: AuthEvent): Pair<AuthState, AuthEffect?> {
        return when (event) {
            is AuthEvent.ValidationError -> {
                Log.d(AUTH_REDUCER_LOG, "AuthEvent.Login")
                previousState.copy(
                    isEmailError = event.emailMessageRes != null,
                    isPasswordValueError = event.passwordMessageRes != null
                ) to AuthEffect.ValidationError(
                    email = event.emailMessageRes,
                    password = event.passwordMessageRes
                )
            }

            is AuthEvent.PasswordChanged -> {
                Log.d(AUTH_REDUCER_LOG, "AuthEvent.PasswordValueChanged")
                //TODO("Validate Password")
                // if error emit effect (snackbar)
                previousState.copy(
                    passwordValue = event.value,
                    isPasswordValueError = false
                ) to null
            }

            is AuthEvent.EmailChanged -> {
                Log.d(AUTH_REDUCER_LOG, "AuthEvent.UserNameValueChanged")
                //TODO("Validate Email")

                previousState.copy(
                    emailValue = event.value,
                    isEmailError = false
                ) to null
            }

            AuthEvent.Loading -> previousState.copy(isLoading = true) to null
        }
    }

    companion object {
        const val AUTH_REDUCER_LOG = "AuthReducer"
    }
}