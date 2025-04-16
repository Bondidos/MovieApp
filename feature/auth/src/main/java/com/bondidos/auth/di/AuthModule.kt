package com.bondidos.auth.di

import com.bondidos.auth.data.repository.AuthRepositoryImpl
import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.utils.AppValidator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAppValidator() = AppValidator()

    @Provides
    fun provideFirebaseAuth() = Firebase.auth
}

@Module
@InstallIn(SingletonComponent::class)
interface AuthModuleBinds {
    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}