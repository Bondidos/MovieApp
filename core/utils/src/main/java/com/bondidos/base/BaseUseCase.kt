package com.bondidos.base

abstract class BaseUseCase<in Params, out T> {
    abstract operator fun invoke(params: Params): T

    operator fun invoke(): T = invoke(Unit as Params)

    object None
}

abstract class SuspendUseCase<in Params, out T> {
    abstract suspend operator fun invoke(params: Params): T

    suspend operator fun invoke(): T = invoke(Unit as Params)
}