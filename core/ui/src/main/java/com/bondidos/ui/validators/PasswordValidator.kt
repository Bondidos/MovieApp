package com.bondidos.ui.validators

import com.bondidos.ui.R

object PasswordValidator {
    fun validatePassword(password: String): Int? {
        val regex = Regex("^(?=.*[A-Z])[A-Za-z0-9]{8,10}$")
        return when {
            password.isEmpty() -> R.string.validation_password_empty
            password.length < 8 -> R.string.validation_password_min
            password.length > 10 -> R.string.validation_password_max
            !regex.matches(password) -> R.string.validation_password_requirements
            else -> null
        }
    }

}