package com.bondidos.auth.domain.extensions

import com.bondidos.auth.domain.model.AuthUser
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toAuthUser() = AuthUser(uid, email)