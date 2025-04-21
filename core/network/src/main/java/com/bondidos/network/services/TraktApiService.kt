package com.bondidos.network.services

import com.bondidos.network.dto.movies.MovieResponseDto
import retrofit2.http.GET

interface TraktApiService {

    @GET("movies/trending")
    suspend fun getTrendingMovies(): List<MovieResponseDto>
}