package com.bondidos.utils

import java.util.regex.Pattern

typealias FormValidationResult = Pair<ValidationResult.EmailValidationResult, ValidationResult.PasswordValidationResult>

class AppValidator {
    fun validateLoginForm(
        email: String,
        password: String,
    ): FormValidationResult =
        validateEmail(email) to validatePassword(password)

    fun validateSingUpForm(
        email: String,
        password: String,
        passwordRetyped: String,
    ) = SingInFormValidationResult(
        email = validateEmail(email),
        password = validatePassword(password),
        passwordRetyped = validatePasswordRetyped(password, passwordRetyped)
    )

    private fun validateEmail(email: String): ValidationResult.EmailValidationResult {
        return when {
            email.isBlank() -> ValidationResult.EmailValidationResult.EmailIsBlank
            email.length > EMAIL_MIN_LENGTH -> ValidationResult.EmailValidationResult.EmailTooLong
            !isValidEmailFormat(email) -> ValidationResult.EmailValidationResult.EmailWrongFormat
            !isValidEmailDomain(email) -> ValidationResult.EmailValidationResult.EmailWrongDomain
            else -> ValidationResult.EmailValidationResult.EmailOk
        }
    }

    private fun validatePassword(password: String): ValidationResult.PasswordValidationResult {
        val regex = Regex("^(?=.*[A-Z])[A-Za-z0-9]{8,10}$")
        return when {
            password.isEmpty() -> ValidationResult.PasswordValidationResult.PasswordIsBlank
            password.length < PASSWORD_MIN_LENGTH -> ValidationResult.PasswordValidationResult.PasswordTooShort
            password.length > PASSWORD_MAX_LENGTH -> ValidationResult.PasswordValidationResult.PasswordTooLong
            !regex.matches(password) -> ValidationResult.PasswordValidationResult.PasswordWrongRequirements
            else -> ValidationResult.PasswordValidationResult.PasswordOk
        }
    }

    private fun validatePasswordRetyped(password: String, retypedPassword: String) =
        if (password == retypedPassword)
            validatePassword(retypedPassword)
        else ValidationResult.PasswordValidationResult.PasswordsNotIdentical

    private fun isValidEmailFormat(email: String): Boolean {
        return Pattern.compile(
            ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+")
        ).matcher(email).matches()
    }

    private fun isValidEmailDomain(email: String): Boolean {
        val domainPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}\$"
        )
        return domainPattern.matcher(email).matches()
    }

    companion object {
        private const val EMAIL_MIN_LENGTH = 36
        private const val PASSWORD_MIN_LENGTH = 8
        private const val PASSWORD_MAX_LENGTH = 12
    }
}
