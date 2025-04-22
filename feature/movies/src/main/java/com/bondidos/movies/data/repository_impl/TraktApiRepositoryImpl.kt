package com.bondidos.movies.data.repository_impl

import com.bondidos.cache.TrendingMoviesDao
import com.bondidos.movies.data.extensions.toTrendingMovie
import com.bondidos.movies.domain.model.AnticipatedMovie
import com.bondidos.movies.domain.model.TrendingMovie
import com.bondidos.movies.domain.repository.TraktApiRepository
import com.bondidos.network.services.TraktApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TraktApiRepositoryImpl @Inject constructor(
    private val traktApiService: TraktApiService,
    private val trendingMovieDao: TrendingMoviesDao
) : TraktApiRepository {
    override fun getTrending(page: Int): Flow<List<TrendingMovie>> = flow {
//        val movies = traktApiService.getTrendingMovies(page)
//        trendingMovieDao.insert(
//            TrendingMoviesCacheEntity(
//                movies = movies,
//                page = page
//            )
//        )
        trendingMovieDao.get()
//        emit(movies.toTrendingMovie())
        emit(trendingMovieDao.get()?.movies?.toTrendingMovie() ?: emptyList())
    }

    override fun getAnticipated(page: Int): Flow<List<AnticipatedMovie>> {
        TODO("Not yet implemented")
    }
}