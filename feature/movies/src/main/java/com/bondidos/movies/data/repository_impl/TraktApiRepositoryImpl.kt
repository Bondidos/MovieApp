package com.bondidos.movies.data.repository_impl

import com.bondidos.cache.dao.AnticipatedMoviesDao
import com.bondidos.cache.entity.TrendingMoviesCacheEntity
import com.bondidos.cache.dao.TrendingMoviesDao
import com.bondidos.cache.entity.AnticipatedMoviesCacheEntity
import com.bondidos.exceptions.CacheExceptions
import com.bondidos.movies.data.extensions.toAnticipatedMovie
import com.bondidos.movies.data.extensions.toTrendingMovie
import com.bondidos.movies.domain.model.Movie
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
    private val trendingMovieDao: TrendingMoviesDao,
    private val anticipatedMoviesDao: AnticipatedMoviesDao
) : TraktApiRepository {
    override fun getTrending(page: Int): Flow<List<Movie>> = flow {

        val trendingCache = getTrendingFromCacheIfActual(page)
        val result = trendingCache.ifEmpty { getTrendingFromRemoteAndCache(page) }
        emit(result)
    }

    override fun getTrendingFromCache(page: Int): Flow<List<Movie>> = flow {
        val cache = trendingMovieDao.get(page = page)
        emit(cache?.movies?.toTrendingMovie() ?: throw CacheExceptions.TrendingMovieEmptyCache)
    }

    override fun getAnticipated(page: Int): Flow<List<Movie>> = flow {

        val anticipatedCache = getAnticipatedFromCacheIfActual(page)
        val result = anticipatedCache.ifEmpty { getAnticipatedFromRemoteAndCache(page) }
        emit(result)
    }

    override fun getAnticipatedFromCache(page: Int): Flow<List<Movie>> = flow {
        val cache = anticipatedMoviesDao.get(page = page)
        emit(
            cache?.movies?.toAnticipatedMovie() ?: throw CacheExceptions.AnticipatedMovieEmptyCache
        )
    }

    private suspend fun getAnticipatedFromCacheIfActual(page: Int): List<Movie> {
        val cache = anticipatedMoviesDao.get(page = page)
        if (DateUtils.isOlderThenNowWithGivenGap(
                timeStamp = cache?.createdAt,
                gap = CACHE_LIVE_TIME_HOURS
            )
        ) {
            return cache?.movies?.toAnticipatedMovie()
                ?: throw CacheExceptions.TrendingMovieEmptyCache
        }
        return emptyList()
    }

    private suspend fun getTrendingFromCacheIfActual(page: Int): List<Movie> {
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

    private suspend fun getTrendingFromRemoteAndCache(page: Int): List<Movie> {
        val movies = traktApiService.getTrendingMovies(page)
        trendingMovieDao.insert(
            TrendingMoviesCacheEntity(
                movies = movies,
                page = page
            )
        )
        return movies.toTrendingMovie()
    }

    private suspend fun getAnticipatedFromRemoteAndCache(page: Int): List<Movie> {
        val movies = traktApiService.getAnticipatedMovies(page)
        anticipatedMoviesDao.insert(
            AnticipatedMoviesCacheEntity(
                movies = movies,
                page = page
            )
        )
        return movies.toAnticipatedMovie()
    }
}