package com.bondidos.movies.data.repository_impl

import com.bondidos.movies.domain.model.AnticipatedMovie
import com.bondidos.movies.domain.model.TrendingMovie
import com.bondidos.movies.domain.repository.TraktApiRepository
import com.bondidos.network.services.TraktApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TraktApiRepositoryImpl @Inject constructor(
    private val traktApiService: TraktApiService
) : TraktApiRepository {
    override fun getTrending(page: Int): Flow<List<TrendingMovie>> = flow {
        val movies = traktApiService.getTrendingMovies(page)
        // TODO Caching Logic
    }

    override fun getAnticipated(page: Int): Flow<List<AnticipatedMovie>> {
        TODO("Not yet implemented")
    }
}