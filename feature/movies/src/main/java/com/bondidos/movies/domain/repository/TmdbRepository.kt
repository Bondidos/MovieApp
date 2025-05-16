package com.bondidos.movies.domain.repository

import com.bondidos.movies.domain.model.people.CastAndCrewImages
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {
    suspend fun fetchImage(id: Int): Flow<CastAndCrewImages>
}