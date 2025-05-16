package com.bondidos.auth.domain.utils

import com.bondidos.auth.domain.model.AuthUser
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo

fun FirebaseUser.toAuthUser() = AuthUser(
    uid,
    email,
    AuthUser.SignInMethod.fromString(extractProviderName(providerData))
)

private fun extractProviderName(providerData: List<UserInfo>) =
    providerData.first { it.providerId != "firebase" }.providerId
