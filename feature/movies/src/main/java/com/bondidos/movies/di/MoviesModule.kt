package com.bondidos.movies.di

import com.bondidos.movies.data.repository_impl.TmdbRepositoryImpl
import com.bondidos.movies.data.repository_impl.TraktApiRepositoryImpl
import com.bondidos.movies.domain.repository.TmdbRepository
import com.bondidos.movies.domain.repository.TraktApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MoviesModuleBinds {

    @Binds
    fun bindsTraktApiRepository(traktApiRepositoryImpl: TraktApiRepositoryImpl): TraktApiRepository

    @Binds
    fun bindsTmdbRepository(tmdbRepositoryImpl: TmdbRepositoryImpl): TmdbRepository
}
