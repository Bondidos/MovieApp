package com.bondidos.auth.di

import com.bondidos.utils.AppValidator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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