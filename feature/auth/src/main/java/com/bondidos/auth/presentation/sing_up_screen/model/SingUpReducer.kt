package com.bondidos.auth.presentation.sing_up_screen.model

import com.bondidos.auth.presentation.sing_up_screen.intent.SingUpEffect
import com.bondidos.auth.presentation.sing_up_screen.intent.SingUpState
import com.bondidos.auth.presentation.sing_up_screen.intent.SingUpEvent

import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class SingUpReducer @Inject constructor() : Reducer<SingUpState, SingUpEvent, SingUpEffect> {
    override fun reduce(
        previousState: SingUpState,
        event: SingUpEvent
    ): Pair<SingUpState, SingUpEffect?> {
        return when (event) {
            is SingUpEvent.Loading -> previousState.copy(isLoading = true) to null
            is SingUpEvent.EmailChanged -> previousState.copy(
                emailValue = event.value,
                isEmailError = false
            ) to null

            is SingUpEvent.PasswordChanged -> previousState.copy(
                passwordValue = event.value,
                isPasswordValueError = false
            ) to null

            is SingUpEvent.PasswordRetypedChanged -> previousState.copy(
                passwordRetypedValue = event.value,
                isPasswordRetypedValueError = false
            ) to null

            is SingUpEvent.EmailValidationError -> previousState.copy(isEmailError = true) to null
            is SingUpEvent.PasswordValidationError -> previousState.copy(isPasswordValueError = true) to null
            is SingUpEvent.PasswordRetypedValidationError -> previousState.copy(
                isPasswordRetypedValueError = true
            ) to null

            is SingUpEvent.ShowValidationErrorSnackBar -> previousState to SingUpEffect.ValidationError(
                listOf(
                    event.email,
                    event.password,
                    event.passwordRetyped,
                )
            )

            is SingUpEvent.SingUpError -> previousState.copy(
                isLoading = false,
            ) to SingUpEffect.SignInError(event.value ?: "Unknown Error")
        }
    }
}