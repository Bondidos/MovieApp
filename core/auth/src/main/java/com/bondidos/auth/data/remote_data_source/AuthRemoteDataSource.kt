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

    fun resetPassword() = flow {
        try {
            firebaseAuth.sendPasswordResetEmail("").await()
            emit(true)
        } catch (_: Throwable) {
            emit(false)
        }
    }

    fun updatePassword(value: String) = flow {
        try {
            firebaseAuth.currentUser?.updatePassword(value)?.await()
            emit(true)
        } catch (_: Throwable) {
            emit(false)
        }
    }
//todo catch exceptions in usecase
    fun deleteUser() = flow {
        try {
//            firebaseAuth.currentUser.reauthenticate()
            firebaseAuth.currentUser?.delete()?.await()
            emit(true)
        } catch (_: Throwable) {
            //ERROR_REQUIRES_RECENT_LOGIN
            //This operation is sensitive and requires recent authentication. Log in again before retrying this request.
            emit(false)
        }
    }
}