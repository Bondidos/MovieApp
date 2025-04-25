package com.bondidos.movies.domain.repository

import com.bondidos.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface TraktApiRepository {
    fun getTrending(page: Int): Flow<List<Movie>>

    fun getTrendingFromCache(page: Int): Flow<List<Movie>>

    fun getAnticipated(page: Int): Flow<List<Movie>>

    fun getAnticipatedFromCache(page: Int): Flow<List<Movie>>
}