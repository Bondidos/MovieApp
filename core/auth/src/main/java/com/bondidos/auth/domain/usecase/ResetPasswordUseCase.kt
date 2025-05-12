package com.bondidos.auth.domain.usecase

import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
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
                emit(UseCaseResult.Error(e.toUseCaseError()))
            }
        }
    }
}