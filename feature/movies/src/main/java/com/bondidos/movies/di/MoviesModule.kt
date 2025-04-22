package com.bondidos.movies.di

import com.bondidos.movies.data.repository_impl.TraktApiRepositoryImpl
import com.bondidos.movies.domain.repository.TraktApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
interface MoviesModuleBinds {

    @Binds
    fun providesTraktApiRepository(traktApiRepository: TraktApiRepositoryImpl): TraktApiRepository
}