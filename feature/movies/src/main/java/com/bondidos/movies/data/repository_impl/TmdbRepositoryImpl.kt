package com.bondidos.movies.data.repository_impl

import com.bondidos.movies.data.extensions.toCastAndCrewImages
import com.bondidos.movies.domain.repository.TmdbRepository
import com.bondidos.network.services.TmdbApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbRepositoryImpl @Inject constructor(
    private val tmdbApiService: TmdbApiService,
) : TmdbRepository {
    //todo cache
    override suspend fun fetchImage(id: Int) = flow {
        val castAndCrewImages = tmdbApiService.getCrewAndCastImagePath(id)
        emit(castAndCrewImages.toCastAndCrewImages())
    }
}