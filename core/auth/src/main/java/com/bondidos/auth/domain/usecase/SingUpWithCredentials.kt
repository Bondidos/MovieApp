package com.bondidos.auth.domain.usecase

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.auth.domain.utils.authUserToUseCaseResult
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseError
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SingUpWithCredentials @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<GetCredentialResponse, Flow<UseCaseResult<AuthUser>>>() {
    override fun invoke(params: GetCredentialResponse): Flow<UseCaseResult<AuthUser>> {
        when (val credential = params.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)

                    val authCredentials =
                        GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    return authRepository.registerWithCredentials(authCredentials)
                        .map(::authUserToUseCaseResult).catch {
                            emit(UseCaseResult.Error(it.toUseCaseError()))
                        }
                }
            }

            else -> {
                return flow {
                    emit(UseCaseResult.Error(UseCaseError(message = "Unexpected type of credential")))
                }
            }
        }
        return flow {
            emit(UseCaseResult.Error(UseCaseError(message = "Unexpected type of credential")))
        }
    }
}