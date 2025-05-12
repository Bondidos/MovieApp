package com.bondidos.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class TraktApiInterceptor(private val traktApiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Content-Type","application/json")
            .header("trakt-api-version","2")
            .header("trakt-api-key",traktApiKey)
            .build()

        return chain.proceed(newRequest)
    }
}