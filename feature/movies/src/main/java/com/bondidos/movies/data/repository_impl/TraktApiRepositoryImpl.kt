package com.bondidos.movies.data.repository_impl

import com.bondidos.cache.dao.AnticipatedMoviesDao
import com.bondidos.cache.dao.CrewAndCastDao
import com.bondidos.cache.dao.TrendingMoviesDao
import com.bondidos.cache.entity.AnticipatedMoviesCacheEntity
import com.bondidos.cache.entity.CrewAndCastEntity
import com.bondidos.cache.entity.TrendingMoviesCacheEntity
import com.bondidos.exceptions.CacheExceptions
import com.bondidos.movies.data.extensions.MovieDtoConverter
import com.bondidos.movies.data.extensions.toCrewAndCastMemberList
import com.bondidos.movies.domain.model.movie.Movie
import com.bondidos.movies.domain.model.people.CrewAndCastMember
import com.bondidos.movies.domain.repository.TraktApiRepository
import com.bondidos.network.dto.people.CrewAndCastDto
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
    private val anticipatedMoviesDao: AnticipatedMoviesDao,
    private val crewAndCastDao: CrewAndCastDao,
    private val movieDtoConverter: MovieDtoConverter
) : TraktApiRepository {
    override fun getTrending(page: Int): Flow<List<Movie>> = flow {

        val trendingCache = getTrendingFromCacheIfActual(page)
        val result = trendingCache.ifEmpty { getTrendingFromRemoteAndCache(page) }
        emit(result)
    }

    override fun getTrendingFromCache(page: Int): Flow<List<Movie>> = flow {
        val cache = trendingMovieDao.get(page = page)
        val data = cache?.movies ?: throw CacheExceptions.TrendingMovieEmptyCache
        emit(movieDtoConverter.toTrendingMovie(data))
    }

    override fun getAnticipated(page: Int): Flow<List<Movie>> = flow {

        val anticipatedCache = getAnticipatedFromCacheIfActual(page)
        val result = anticipatedCache.ifEmpty { getAnticipatedFromRemoteAndCache(page) }
        emit(result)
    }

    override fun getAnticipatedFromCache(page: Int): Flow<List<Movie>> = flow {
        val cache = anticipatedMoviesDao.get(page = page)
        val data = cache?.movies ?: throw CacheExceptions.AnticipatedMovieEmptyCache
        emit(movieDtoConverter.toAnticipatedMovie(data))
    }

    override fun getTrendingMovieDetails(traktId: Int?, page: Int) = flow {
        val movieDto =
            trendingMovieDao.get(page)?.movies?.find { movie -> movie.movie.ids.trakt == traktId }
        emit(movieDtoConverter.toMovieDetails(movieDto))
    }

    override fun getAnticipatedMovieDetails(traktId: Int?, page: Int) = flow {
        val movieDto =
            anticipatedMoviesDao.get(page)?.movies?.find { movie -> movie.movie.ids.trakt == traktId }
        emit(movieDtoConverter.toMovieDetails(movieDto))
    }

    override fun getCastAndCrew(traktId: Int): Flow<List<CrewAndCastMember>> = flow {
        val crewAndCastDto = getCrewAndCastFromCacheIfActual(traktId)
        val result = crewAndCastDto ?: getCrewAndCastFromRemoteAndCache(traktId)
        emit(result.toCrewAndCastMemberList())
    }

    private suspend fun getCrewAndCastFromCacheIfActual(id: Int): CrewAndCastDto? {
        val cache = crewAndCastDao.get(id = id)
        if (DateUtils.isOlderThenNowWithGivenGap(
                timeStamp = cache?.createdAt,
                gap = CACHE_LIVE_TIME_HOURS
            )
        ) {
            return cache?.crewAndCast
        }
        return null
    }

    private suspend fun getAnticipatedFromCacheIfActual(page: Int): List<Movie> {
        val cache = anticipatedMoviesDao.get(page = page)
        if (DateUtils.isOlderThenNowWithGivenGap(
                timeStamp = cache?.createdAt,
                gap = CACHE_LIVE_TIME_HOURS
            )
        ) {
            val data = cache?.movies ?: throw CacheExceptions.AnticipatedMovieEmptyCache
            return movieDtoConverter.toAnticipatedMovie(data)
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
            val data = cache?.movies ?: throw CacheExceptions.TrendingMovieEmptyCache
            return movieDtoConverter.toTrendingMovie(data)
        }
        return emptyList()
    }


    private suspend fun getCrewAndCastFromRemoteAndCache(id: Int): CrewAndCastDto {
        val crewAndCast = traktApiService.getCrewAndCast(id)
        crewAndCastDao.insert(
            CrewAndCastEntity(
                id = id,
                crewAndCast = crewAndCast
            )
        )
        return crewAndCast
    }

    private suspend fun getTrendingFromRemoteAndCache(page: Int): List<Movie> {
        val movies = traktApiService.getTrendingMovies(page)
        trendingMovieDao.insert(
            TrendingMoviesCacheEntity(
                movies = movies,
                page = page
            )
        )
        return movieDtoConverter.toTrendingMovie(movies)
    }

    private suspend fun getAnticipatedFromRemoteAndCache(page: Int): List<Movie> {
        val movies = traktApiService.getAnticipatedMovies(page)
        anticipatedMoviesDao.insert(
            AnticipatedMoviesCacheEntity(
                movies = movies,
                page = page
            )
        )
        return movieDtoConverter.toAnticipatedMovie(movies)
    }
}