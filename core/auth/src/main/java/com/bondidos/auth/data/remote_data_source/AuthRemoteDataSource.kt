package com.bondidos.auth.data.remote_data_source

import com.bondidos.auth.domain.utils.toAuthUser
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun login(email: String, password: String) = flow {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(result.user?.toAuthUser())
    }

    fun singIn(email: String, password: String) = flow {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(result.user?.toAuthUser())
    }

    fun getCurrentUser() = flow {
        val result = firebaseAuth.currentUser
        emit(result?.toAuthUser())
    }

    fun singInWithCredentials(credentials: AuthCredential) = flow {
        val result = firebaseAuth.signInWithCredential(credentials).await()
        emit(result.user?.toAuthUser())
    }

    fun singOut() = firebaseAuth.signOut()

    suspend fun resetPassword() {
        val user = firebaseAuth.currentUser ?: throw IllegalStateException("No authenticated user")
        val email = user.email ?: throw NullPointerException("User email is null")
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    suspend fun updatePassword(value: String) {
        firebaseAuth.currentUser?.updatePassword(value)?.await()
    }

    suspend fun deleteCurrentUser() {
        firebaseAuth.currentUser?.delete()?.await()
    }
}