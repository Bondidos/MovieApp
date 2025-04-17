package com.bondidos.auth.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.bondidos.ui.R
import com.bondidos.auth.data.repository.AuthRepositoryImpl
import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.utils.AppValidator
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAppValidator() = AppValidator()

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideGetGoogleIdOption(@ApplicationContext context: Context) = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.auth_server_client_id))
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    fun provideGetCredentialRequest(credentialOptions: GetGoogleIdOption) =
        GetCredentialRequest.Builder()
            .addCredentialOption(credentialOptions)
            .build()

    @Provides
    fun provideCredentialManager(@ApplicationContext context: Context) =
        CredentialManager.create(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface AuthModuleBinds {
    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}