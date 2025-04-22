package com.bondidos.movies.domain.repository

import com.bondidos.movies.domain.model.AnticipatedMovie
import com.bondidos.movies.domain.model.TrendingMovie
import kotlinx.coroutines.flow.Flow

interface TraktApiRepository {
    fun getTrending(page: Int): Flow<List<TrendingMovie>>

    fun getAnticipated(page: Int): Flow<List<AnticipatedMovie>>
}