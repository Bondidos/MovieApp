package com.bondidos.base

sealed class UseCaseResult<out T> {
    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Error(val error: UseCaseError) : UseCaseResult<Nothing>()
}

data class UseCaseError(
    val message: String? = null,
    val throwable: Throwable? = null,
    val errorCode: Int? = null
) {
    companion object {
        const val FIREBASE_AUTH_INVALID_CREDENTIALS = 1
    }
}

fun Throwable.toUseCaseError(errorCode: Int? = null): UseCaseError {
    return UseCaseError(
        message = message,
        throwable = this,
        errorCode = errorCode
    )
}