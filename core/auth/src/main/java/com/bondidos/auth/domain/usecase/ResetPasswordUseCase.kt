package com.bondidos.auth.domain.usecase

import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, Flow<UseCaseResult<Unit>>>() {
    override fun invoke(params: Unit): Flow<UseCaseResult<Unit>> {
        return authRepository.resetPassword()
            .map {
                if (it) UseCaseResult.Success(Unit)
                else UseCaseResult.Error(Exception("Error while resetting password").toUseCaseError())
            }
    }
}