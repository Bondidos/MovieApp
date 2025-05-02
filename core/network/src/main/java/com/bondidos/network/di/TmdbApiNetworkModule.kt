package com.bondidos.network.di

import com.bondidos.network.ApiConst
import com.bondidos.network.interceptors.TmdbApiInterceptor
import com.bondidos.network.services.TmdbApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TmdbApiNetworkModule {

    @Provides
    @Singleton
    fun provideTmdbApiInterceptor() = TmdbApiInterceptor()

    @TmdbApiRetrofit
    @Provides
    @Singleton
    fun provideTmdbApiOkHttpClient(
        okHttpClient: OkHttpClient,
        interceptor: TmdbApiInterceptor,
        loginInterceptor: HttpLoggingInterceptor,
    ) = okHttpClient.newBuilder()
        .addInterceptor(interceptor)
        .addInterceptor(loginInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @TmdbApiRetrofit
    @Provides
    @Singleton
    fun provideRetrofitTmdb(
        @TmdbApiRetrofit okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConst.TMDB_API)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun tmdbApiService(
        @TmdbApiRetrofit tmdbApiRetrofit: Retrofit
    ): TmdbApiService = tmdbApiRetrofit.create(TmdbApiService::class.java)
}