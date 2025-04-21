package com.bondidos.network.di

import com.bondidos.network.interceptors.TraktApiInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImdbApiNetworkModule {

//    @ImdbApiRetrofit
//    @Provides
//    @Singleton
//    fun provideRetrofitImdb(
//        okHttpClient: OkHttpClient,
//        interceptor: TraktApiInterceptor,
//        loginInterceptor: HttpLoggingInterceptor,
//        moshi: Moshi
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(ApiConst.TRAKT_API)
//            .client(
//                okHttpClient.newBuilder()
//                    .addInterceptor(interceptor)
//                    .addInterceptor(loginInterceptor)
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(30, TimeUnit.SECONDS)
//                    .build()
//            )
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
}