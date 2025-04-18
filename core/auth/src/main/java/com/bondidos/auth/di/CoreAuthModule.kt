package com.bondidos.auth.di

import com.bondidos.auth.data.repository.AuthRepositoryImpl
import com.bondidos.auth.domain.repository.AuthRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreAuthModule {

    @Provides
    fun provideFireBaseAuth() = Firebase.auth
}

@Module
@InstallIn(SingletonComponent::class)
interface CoreAuthModuleBinds {
    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}