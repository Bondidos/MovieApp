package com.bondidos.utils

data class SingInFormValidationResult(
    val email: ValidationResult.EmailValidationResult,
    val password: ValidationResult.PasswordValidationResult,
    val passwordRetyped: ValidationResult.PasswordValidationResult,
) {
    fun isFormValid() =
        email.isEmailValid() && password.isPasswordValid() && passwordRetyped.isPasswordValid()
}
