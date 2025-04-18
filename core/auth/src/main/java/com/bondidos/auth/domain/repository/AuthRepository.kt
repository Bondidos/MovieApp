package com.bondidos.auth.domain.repository

import com.bondidos.auth.domain.model.AuthUser
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<AuthUser?>
    fun register(email: String, password: String): Flow<AuthUser?>
    fun getCurrentUser(): Flow<AuthUser?>
    fun registerWithCredentials(credentials: AuthCredential): Flow<AuthUser?>
}