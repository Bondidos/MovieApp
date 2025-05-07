package com.bondidos.auth.domain.usecase

import com.bondidos.auth.domain.repository.AuthRepository
import com.bondidos.auth.domain.usecase.model.ChangePasswordParams
import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<ChangePasswordParams, Flow<UseCaseResult<Unit>>>() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(params: ChangePasswordParams): Flow<UseCaseResult<Unit>> {
        return authRepository.getCurrentUser()
            .flatMapConcat { user ->
                user?.let {
                    authRepository.login(user.email!!, params.oldPassword)
                } ?: flow { emit(null) }
            }
            .flatMapConcat { authUser ->
                authUser?.let {
                    authRepository.updatePassword(params.newPassword)
                } ?: flow { emit(false) }
            }.map {
                if (it) UseCaseResult.Success(Unit)
                else UseCaseResult.Error(Exception("Error while Changing password").toUseCaseError())
            }
            .catch { emit(UseCaseResult.Error(it.toUseCaseError())) }
    }
}