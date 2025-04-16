package com.bondidos.auth.data.repository

import com.bondidos.auth.data.remote_data_source.AuthRemoteDataSource
import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override fun login(email: String, password: String): Flow<AuthUser?> =
        authRemoteDataSource.login(email, password)

    override fun register(email: String, password: String): Flow<AuthUser?> =
        authRemoteDataSource.singIn(email, password)

    override fun getCurrentUser(): Flow<AuthUser?> = authRemoteDataSource.getCurrentUser()
}