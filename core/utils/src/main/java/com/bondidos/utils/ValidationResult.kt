package com.bondidos.utils

enum class ValidationResult {
    EmailIsBlank,// R.string.validation_email_empty
    EmailTooLong,//R.string.validation_email_too_long
    EmailWrongFormat,//R.string.validation_email_format
    EmailWrongDomain,//R.string.validation_email_domain
    EmailOk,

    PasswordIsBlank,//R.string.validation_password_empty
    PasswordTooLong,//R.string.validation_password_max
    PasswordTooShort,//R.string.validation_password_min
    PasswordWrongRequirements,//R.string.validation_password_requirements
    PasswordOk;

    fun isEmailValid() = this == EmailOk
    fun isPasswordValid() = this == PasswordOk
}