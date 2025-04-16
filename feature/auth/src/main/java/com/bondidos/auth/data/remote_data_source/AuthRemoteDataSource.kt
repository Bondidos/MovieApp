package com.bondidos.auth.data.remote_data_source

import com.bondidos.auth.domain.extensions.toAuthUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
){
    fun login(email: String, password: String) = flow {
        val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
        emit(result.user?.toAuthUser())
    }

    fun singIn(email: String, password: String) = flow {
        val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
        emit(result.user?.toAuthUser())
    }

    fun getCurrentUser() = flow {
        val result = firebaseAuth.currentUser
        emit(result?.toAuthUser())
    }
}