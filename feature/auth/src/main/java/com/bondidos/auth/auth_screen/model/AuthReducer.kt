package com.bondidos.auth.auth_screen.model

import android.util.Log
import com.bondidos.auth.auth_screen.intent.AuthEffect
import com.bondidos.auth.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.ui.validators.EmailValidator
import com.bondidos.ui.validators.PasswordValidator
import javax.inject.Inject

// TODO EVENTS TO FIREBASE
class AuthReducer @Inject constructor() : Reducer<AuthState, AuthEvent, AuthEffect> {
    override fun reduce(previousState: AuthState, event: AuthEvent): Pair<AuthState, AuthEffect?> {
        when (event) {
            is AuthEvent.Login -> {
                Log.d(AUTH_REDUCER_LOG, "AuthEvent.Login")
                //TODO validation Logic
                val emailValidation = EmailValidator.validateEmail(previousState.emailValue)
                val passwordValidation =
                    PasswordValidator.validatePassword(previousState.passwordValue)
                // todo do not work correctly while 2 errors in same time
                emailValidation?.let {
                    return previousState.copy(isEmailError = true) to null
                }
                passwordValidation?.let {
                    return previousState.copy(isPasswordValueError = true) to null
                }
                return previousState to null
            }

            AuthEvent.LoginWithGoogle -> {
                Log.d(AUTH_REDUCER_LOG, "AuthEvent.LoginWithGoogle")
                return previousState to null
            }

            is AuthEvent.PasswordValueChanged -> {
                Log.d(AUTH_REDUCER_LOG, "AuthEvent.PasswordValueChanged")
                //TODO("Validate Password")
                // if error emit effect (snackbar)
                return previousState.copy(
                    passwordValue = event.value,
                    isPasswordValueError = false
                ) to null
            }

            is AuthEvent.EmailValueChanged -> {
                Log.d(AUTH_REDUCER_LOG, "AuthEvent.UserNameValueChanged")
                //TODO("Validate Email")

                return previousState.copy(
                    emailValue = event.value,
                    isEmailError = false
                ) to null
            }

            AuthEvent.SingUp -> TODO()
        }
    }

    companion object {
        const val AUTH_REDUCER_LOG = "AuthReducer"
    }
}