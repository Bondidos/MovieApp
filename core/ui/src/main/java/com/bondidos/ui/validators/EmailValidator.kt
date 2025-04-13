package com.bondidos.ui.validators

import android.util.Patterns
import com.bondidos.ui.R
import java.util.regex.Pattern

object EmailValidator {
    fun validateEmail(email: String): Int? {
        return when {
            email.isBlank() -> R.string.validation_email_empty
            email.length > 18 -> R.string.validation_email_too_long
            !isValidEmailFormat(email) -> R.string.validation_email_format
            !isValidEmailDomain(email) -> R.string.validation_email_domain
            else -> null
        }
    }

    private fun isValidEmailFormat(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidEmailDomain(email: String): Boolean {
        val domainPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}\$"
        )
        return domainPattern.matcher(email).matches()
    }
}