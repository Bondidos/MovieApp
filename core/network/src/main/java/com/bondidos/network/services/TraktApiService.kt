package com.bondidos.network.services

import com.bondidos.network.dto.movies.anticipated.AnticipatedMovieDto
import com.bondidos.network.dto.movies.trending.TrendingMovieDto
import com.bondidos.network.dto.people.CrewAndCastDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TraktApiService {

    @GET("movies/trending")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = "full"
    ): List<TrendingMovieDto>

    @GET("movies/anticipated")
    suspend fun getAnticipatedMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = "full"
    ): List<AnticipatedMovieDto>

    @GET("movies/{id}/people")
    suspend fun getCrewAndCast(
        @Path("id") id: Int
    ): CrewAndCastDto
}