package com.bondidos.auth.domain.utils

import com.bondidos.auth.domain.model.AuthUser
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toAuthUser() = AuthUser(uid, email)