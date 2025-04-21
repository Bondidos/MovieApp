package com.bondidos.network.services

import com.bondidos.network.dto.movies.anticipated.AnticipatedMovieDto
import com.bondidos.network.dto.movies.trending.TrendingMovieDto
import retrofit2.http.GET

interface TraktApiService {

    @GET("movies/trending")
    suspend fun getTrendingMovies(): List<TrendingMovieDto>

    @GET("movies/anticipated")
    suspend fun getAnticipatedMovies(): List<AnticipatedMovieDto>

}