package com.bondidos.network.di

import com.bondidos.network.ApiConst
import com.bondidos.network.interceptors.TraktApiInterceptor
import com.bondidos.network.services.TraktApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object TraktApiNetworkModule {

    @Provides
    @Singleton
    fun provideTraktApiInterceptor() = TraktApiInterceptor()

    @TraktApiRetrofit
    @Provides
    @Singleton
    fun provideTraktApiOkHttpClient(
        okHttpClient: OkHttpClient,
        interceptor: TraktApiInterceptor,
        loginInterceptor: HttpLoggingInterceptor,
    ) = okHttpClient.newBuilder()
        .addInterceptor(interceptor)
        .addInterceptor(loginInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @TraktApiRetrofit
    @Provides
    @Singleton
    fun provideRetrofitTrakt(
        @TraktApiRetrofit okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConst.TRAKT_API)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun traktApiService(
        @TraktApiRetrofit traktApiRetrofit: Retrofit
    ): TraktApiService = traktApiRetrofit.create(TraktApiService::class.java)
}