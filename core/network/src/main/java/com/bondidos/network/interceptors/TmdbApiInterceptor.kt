package com.bondidos.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class TmdbApiInterceptor(private val tmdbApiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", tmdbApiKey)
            .build()

        return chain.proceed(newRequest)
    }
}