package com.bondidos.auth.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.bondidos.ui.composables.clickable.SignWithGoogleButton
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SingWithGoogle(
    onClick: (GetCredentialResponse?) -> Unit,
) {
    val context = LocalContext.current

    SignWithGoogleButton(onClick = {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                onClick(authWithGoogle(context))
            } catch (e: Throwable){
                onClick(null)
            }
        }
    })
}

private suspend fun authWithGoogle(context: Context): GetCredentialResponse {
    val option = provideGetGoogleIdOption(context)
    val request = provideGetCredentialRequest(option)
    val credentialManager = provideCredentialManager(context)

    return credentialManager.getCredential(context, request)
}

private fun provideGetGoogleIdOption(context: Context) = GetGoogleIdOption.Builder()
    .setFilterByAuthorizedAccounts(false)
    .setServerClientId(context.getString(com.bondidos.ui.R.string.auth_server_client_id))
    .setAutoSelectEnabled(true)
    .build()

fun provideGetCredentialRequest(credentialOptions: GetGoogleIdOption) =
    GetCredentialRequest.Builder()
        .addCredentialOption(credentialOptions)
        .build()

fun provideCredentialManager(context: Context) =
    CredentialManager.create(context)