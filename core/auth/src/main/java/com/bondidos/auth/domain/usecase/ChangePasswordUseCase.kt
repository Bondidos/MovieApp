package com.bondidos.auth.domain.usecase

import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.auth.domain.usecase.model.ChangePasswordParams
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseError
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<ChangePasswordParams, Flow<UseCaseResult<Unit>>>() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(params: ChangePasswordParams): Flow<UseCaseResult<Unit>> {
        return authRepository.getCurrentUser()
            .flatMapConcat { user ->
                user?.email?.let { email ->
                    authRepository.login(email, params.oldPassword)
                } ?: flow { emit(null) }
            }
            .flatMapConcat { authUser ->
                flow {
                    authUser?.let {
                        try {
                            authRepository.updatePassword(params.newPassword)
                            emit(UseCaseResult.Success(Unit))
                        } catch (e: Throwable) {
                            emit(UseCaseResult.Error(e.toUseCaseError()))
                        }
                    }
                }
            }
            .catch { exception ->
                if (exception is FirebaseAuthInvalidCredentialsException)
                    emit(
                        UseCaseResult.Error(
                            exception.toUseCaseError(
                                UseCaseError.FIREBASE_AUTH_INVALID_CREDENTIALS
                            )
                        )
                    )
                else
                    emit(UseCaseResult.Error(exception.toUseCaseError()))
            }
    }
}