package com.bondidos.network.services

import com.bondidos.network.dto.people.CastAndCrewImagesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbApiService {
    @GET("/3/person/{id}/images")
    suspend fun getCrewAndCastImagePath(
        @Path("id") id: Int
    ): CastAndCrewImagesDto
}