package com.bondidos.utils

import java.util.regex.Pattern

class AppValidator {
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.EmailIsBlank
            email.length > 18 -> ValidationResult.EmailTooLong
            !isValidEmailFormat(email) -> ValidationResult.EmailWrongFormat
            !isValidEmailDomain(email) -> ValidationResult.EmailWrongDomain
            else -> ValidationResult.EmailOk
        }
    }

    fun validatePassword(password: String): ValidationResult {
        val regex = Regex("^(?=.*[A-Z])[A-Za-z0-9]{8,10}$")
        return when {
            password.isEmpty() -> ValidationResult.PasswordIsBlank
            password.length < 8 -> ValidationResult.PasswordTooShort
            password.length > 10 -> ValidationResult.PasswordTooLong
            !regex.matches(password) -> ValidationResult.PasswordWrongRequirements
            else -> ValidationResult.PasswordOk
        }
    }

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
}