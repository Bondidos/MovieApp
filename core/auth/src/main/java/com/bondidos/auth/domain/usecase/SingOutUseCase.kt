package com.bondidos.auth.domain.usecase

import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SingOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, Flow<UseCaseResult<Unit>>>() {
    override fun invoke(params: Unit): Flow<UseCaseResult<Unit>> = flow {
        authRepository.loginOut()
        emit(UseCaseResult.Success(Unit))
    }
}