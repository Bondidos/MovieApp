package com.bondidos.auth.presentation.sing_up_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.utils.ValidationResult

@Immutable
sealed class SingUpEvent : Reducer.ViewEvent {
    data object Loading : SingUpEvent()
    data class EmailChanged(val value: String) : SingUpEvent()
    data class EmailValidationError(val validationResult: ValidationResult) : SingUpEvent()
    data class PasswordValidationError(val validationResult: ValidationResult) : SingUpEvent()
    data class PasswordRetypedValidationError(val validationResult: ValidationResult) : SingUpEvent()
    data class PasswordChanged(val value: String) : SingUpEvent()
    data class PasswordRetypedChanged(val value: String) : SingUpEvent()
    data class ShowValidationErrorSnackBar private constructor(
        val email: ValidationResult?,
        val password: ValidationResult?,
        val passwordRetyped: ValidationResult?
    ) : SingUpEvent() {

        class Builder {
            private var email: ValidationResult? = null
            private var password: ValidationResult? = null
            private var passwordRetyped: ValidationResult? = null

            fun setEmail(email: ValidationResult?) = apply { this.email = email }
            fun setPassword(password: ValidationResult?) = apply { this.password = password }
            fun setPasswordRetyped(passwordRetyped: ValidationResult?) = apply { this.passwordRetyped = passwordRetyped }

            fun build() = ShowValidationErrorSnackBar(
                email = email,
                password = password,
                passwordRetyped = passwordRetyped
            )
        }

        companion object {
            fun builder() = Builder()
        }
    }
    data class SingUpError(val value: String?) : SingUpEvent()
}
