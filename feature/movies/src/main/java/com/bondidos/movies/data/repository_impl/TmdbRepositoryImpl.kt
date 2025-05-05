package com.bondidos.movies.data.repository_impl

import com.bondidos.cache.dao.CrewAndCastImagesDao
import com.bondidos.cache.entity.CrewAndCastImageEntity
import com.bondidos.exceptions.CacheExceptions
import com.bondidos.movies.data.extensions.toCastAndCrewImages
import com.bondidos.movies.domain.model.people.CastAndCrewImages
import com.bondidos.movies.domain.repository.TmdbRepository
import com.bondidos.network.dto.people.CastAndCrewImagesDto
import com.bondidos.network.services.TmdbApiService
import com.bondidos.utils.DateUtils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

private const val CACHE_LIVE_TIME_HOURS = 5

@Singleton
class TmdbRepositoryImpl @Inject constructor(
    private val tmdbApiService: TmdbApiService,
    private val crewAndCastImagesDao: CrewAndCastImagesDao,
) : TmdbRepository {
    override suspend fun fetchImage(id: Int) = flow {
        val cache = getImagesFromCacheIfActual(id)
        emit(cache ?: fetchFromRemoteAndSave(id))
    }

    private suspend fun getImagesFromCacheIfActual(id: Int): CastAndCrewImages? {
        val cache = crewAndCastImagesDao.get(id = id)
        if (DateUtils.isOlderThenNowWithGivenGap(
                timeStamp = cache?.createdAt,
                gap = CACHE_LIVE_TIME_HOURS
            )
        ) {
            return cache?.let {
                CastAndCrewImagesDto(
                    cache.id,
                    cache.profiles,
                ).toCastAndCrewImages()
            }
                ?: throw CacheExceptions.TrendingMovieEmptyCache
        }
        return null
    }

    private suspend fun fetchFromRemoteAndSave(id: Int): CastAndCrewImages {
        val castAndCrewImages = tmdbApiService.getCrewAndCastImagePath(id)
        crewAndCastImagesDao.insert(
            CrewAndCastImageEntity(
                id = castAndCrewImages.id,
                profiles = castAndCrewImages.profiles,
            )
        )
        return castAndCrewImages.toCastAndCrewImages()
    }
}