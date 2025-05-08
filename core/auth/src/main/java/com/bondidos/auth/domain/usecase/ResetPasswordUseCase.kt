package com.bondidos.auth.domain.usecase

import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseError
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, Flow<UseCaseResult<Unit>>>() {
    override fun invoke(params: Unit): Flow<UseCaseResult<Unit>> {
        return flow {
            try {
                authRepository.resetPassword()
                emit(UseCaseResult.Success(Unit))
            } catch (e: Throwable) {
                if (e is FirebaseAuthInvalidCredentialsException)
                    emit(
                        UseCaseResult.Error(
                            e.toUseCaseError(
                                UseCaseError.FIREBASE_AUTH_INVALID_CREDENTIALS
                            )
                        )
                    )
                else
                    emit(UseCaseResult.Error(e.toUseCaseError()))
            }
        }
    }
}