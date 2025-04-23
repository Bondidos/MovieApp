package com.bondidos.movies.data.repository_impl

import com.bondidos.cache.TrendingMoviesCacheEntity
import com.bondidos.cache.TrendingMoviesDao
import com.bondidos.exceptions.CacheExceptions
import com.bondidos.movies.data.extensions.toTrendingMovie
import com.bondidos.movies.domain.model.AnticipatedMovie
import com.bondidos.movies.domain.model.TrendingMovie
import com.bondidos.movies.domain.repository.TraktApiRepository
import com.bondidos.network.services.TraktApiService
import com.bondidos.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

private const val CACHE_LIVE_TIME_HOURS = 5

@Singleton
class TraktApiRepositoryImpl @Inject constructor(
    private val traktApiService: TraktApiService,
    private val trendingMovieDao: TrendingMoviesDao
) : TraktApiRepository {
    override fun getTrending(page: Int): Flow<List<TrendingMovie>> = flow {

        val trendingCache = getTrendingFromCacheIfActual(page)
        val result = trendingCache.ifEmpty { getTrendingFromRemoteAndCache(page) }
        emit(result)
    }

    override fun getTrendingFromCache(page: Int): Flow<List<TrendingMovie>> = flow {
        val cache = trendingMovieDao.get(page = page)
        emit(cache?.movies?.toTrendingMovie() ?: throw CacheExceptions.TrendingMovieEmptyCache)
    }

    override fun getAnticipated(page: Int): Flow<List<AnticipatedMovie>> {
        TODO("Not yet implemented")
    }

    private suspend fun getTrendingFromCacheIfActual(page: Int): List<TrendingMovie> {
        val cache = trendingMovieDao.get(page = page)
        if (DateUtils.isOlderThenNowWithGivenGap(
                timeStamp = cache?.createdAt,
                gap = CACHE_LIVE_TIME_HOURS
            )
        ) {
            return cache?.movies?.toTrendingMovie() ?: throw CacheExceptions.TrendingMovieEmptyCache
        }
        return emptyList()
    }

    private suspend fun getTrendingFromRemoteAndCache(page: Int): List<TrendingMovie> {
        val movies = traktApiService.getTrendingMovies(page)
        trendingMovieDao.insert(
            TrendingMoviesCacheEntity(
                movies = movies,
                page = page
            )
        )
        return movies.toTrendingMovie()
    }
}