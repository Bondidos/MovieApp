package com.bondidos.movies.domain.repository

import com.bondidos.movies.domain.model.Movie
import com.bondidos.movies.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface TraktApiRepository {
    fun getTrending(page: Int): Flow<List<Movie>>

    fun getTrendingFromCache(page: Int): Flow<List<Movie>>

    fun getAnticipated(page: Int): Flow<List<Movie>>

    fun getAnticipatedFromCache(page: Int): Flow<List<Movie>>

    fun getTrendingMovieDetails(traktId: Int?, page: Int): Flow<MovieDetails?>

    fun getAnticipatedMovieDetails(traktId: Int?, page: Int): Flow<MovieDetails?>
}