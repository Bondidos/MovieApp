package com.bondidos.auth.domain.utils

import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.base.UseCaseError
import com.bondidos.base.UseCaseResult

fun authUserToUseCaseResult(authUser: AuthUser?): UseCaseResult<AuthUser> =
    if (authUser != null) UseCaseResult.Success(authUser) else UseCaseResult.Error(
        UseCaseError(
            "AuthUser is null"
        )
    )