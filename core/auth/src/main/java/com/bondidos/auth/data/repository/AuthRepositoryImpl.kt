package com.bondidos.auth.data.repository

import com.bondidos.auth.data.remote_data_source.AuthRemoteDataSource
import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.auth.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override fun login(email: String, password: String): Flow<AuthUser?> =
        authRemoteDataSource.login(email, password)

    override fun loginOut() = authRemoteDataSource.singOut()

    override fun register(email: String, password: String): Flow<AuthUser?> =
        authRemoteDataSource.singIn(email, password)

    override fun getCurrentUser(): Flow<AuthUser?> = authRemoteDataSource.getCurrentUser()

    override fun registerWithCredentials(credentials: AuthCredential): Flow<AuthUser?> =
        authRemoteDataSource.singInWithCredentials(credentials)

    override fun deleteCurrentUser() = authRemoteDataSource.deleteUser()

    override fun updatePassword(value: String) = authRemoteDataSource.updatePassword(value)

    override fun resetPassword() = authRemoteDataSource.resetPassword()
}