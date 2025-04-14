package com.bondidos.utils

enum class ValidationResult {
    EmailIsBlank,
    EmailTooLong,
    EmailWrongFormat,
    EmailWrongDomain,
    EmailOk,

    PasswordIsBlank,
    PasswordTooLong,
    PasswordTooShort,
    PasswordWrongRequirements,
    PasswordOk;

    fun isEmailValid() = this == EmailOk
    fun isPasswordValid() = this == PasswordOk
}