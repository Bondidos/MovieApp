package com.bondidos.base

sealed class UseCaseResult<out T> {
    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Error(val error: UseCaseError) : UseCaseResult<Nothing>()
}

data class UseCaseError(
    val message: String? = null,
    val throwable: Throwable? = null,
    val errorCode: Int? = null
)

fun Throwable.toUseCaseError(): UseCaseError {
    return UseCaseError(
        message = message,
        throwable = this
    )
}