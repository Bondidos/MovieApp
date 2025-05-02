package com.bondidos.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class TmdbApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
//todo: Hide api key
        val newRequest = originalRequest.newBuilder()
            .header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NTAwYzQwOWM3ZTg0NzNhYzM0YjcyOTk4MjViNDk1MiIsIm5iZiI6MTc0NDEwODQ4Ny4wMiwic3ViIjoiNjdmNGZiYzcxYmIwNDcyODk5OTk5ZTQyIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.9jwnPRAQEMlGfp0nyqBRD8mxM5AKX_hmiogEYDuHYL8")
            .build()

        return chain.proceed(newRequest)
    }
}