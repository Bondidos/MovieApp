package com.bondidos.utils

interface ValidationResult {
    enum class EmailValidationResult: ValidationResult {
        EmailIsBlank,
        EmailTooLong,
        EmailWrongFormat,
        EmailWrongDomain,
        EmailOk;

        fun isEmailValid() = this == EmailOk
    }

    enum class PasswordValidationResult: ValidationResult {
        PasswordIsBlank,
        PasswordTooLong,
        PasswordTooShort,
        PasswordWrongRequirements,
        PasswordsNotIdentical,
        PasswordOk;

        fun isPasswordValid() = this == PasswordOk
    }
}
