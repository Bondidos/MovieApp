package com.bondidos.auth.domain.model

data class AuthUser(
    val id: String,
    val email: String?,
    val signInMethod: SignInMethod
) {
    enum class SignInMethod {
        Email,
        Google,
        Unknown;

        companion object {
            fun fromString(value: String) = when (value) {
                "google.com" -> Google
                "password" -> Email
                else -> Unknown
            }
        }
    }
}
