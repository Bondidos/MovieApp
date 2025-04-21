package com.bondidos.network.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TraktApiRetrofit

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ImdbApiRetrofit