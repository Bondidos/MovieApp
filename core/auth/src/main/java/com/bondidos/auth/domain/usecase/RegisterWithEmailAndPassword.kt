package com.bondidos.auth.domain.usecase

import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.auth.domain.utils.authUserToUseCaseResult
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterWithEmailAndPassword @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Pair<String, String>, Flow<UseCaseResult<AuthUser>>>() {
    override fun invoke(params: Pair<String, String>): Flow<UseCaseResult<AuthUser>> {
        return authRepository.register(params.first, params.second)
            .map(::authUserToUseCaseResult)
            .catch {
                emit(UseCaseResult.Error(it.toUseCaseError()))
            }
    }
}