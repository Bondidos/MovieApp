package com.bondidos.auth.domain.usecase.model

data class ChangePasswordParams(
    val oldPassword: String,
    val newPassword: String,
)
