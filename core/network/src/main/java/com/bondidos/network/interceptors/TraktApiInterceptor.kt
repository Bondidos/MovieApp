package com.bondidos.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class TraktApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
//todo hide key
        val newRequest = originalRequest.newBuilder()
            .header("Content-Type","application/json")
            .header("trakt-api-version","2")
            .header("trakt-api-key","ee2459a4e47a5397fd9b8aead2bd99977572a5d905c80e1d1a440aae7f1a63d3")
            .build()

        return chain.proceed(newRequest)
    }
}