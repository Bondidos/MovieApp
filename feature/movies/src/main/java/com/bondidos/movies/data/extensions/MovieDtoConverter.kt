package com.bondidos.movies.data.extensions

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.bondidos.movies.domain.model.AnticipatedMovie
import com.bondidos.movies.domain.model.TrendingMovie
import com.bondidos.network.dto.movies.anticipated.AnticipatedMovieDto
import com.bondidos.network.dto.movies.trending.TrendingMovieDto
import kotlin.math.roundToInt

private const val imageUrl = "http://img.omdbapi.com/?apikey="
private const val apiKey = "a8c50fd3" //todo Hide ApiKey

fun List<TrendingMovieDto>.toTrendingMovie(): List<TrendingMovie> {
    fun durationToString(params: Int): String {
        val hours = params / 60
        val minutes = params - hours * 60
        if (hours == 0) return " ${minutes}m"
        return " ${hours}h ${minutes}m"
    }

    fun movieIdToImage(id: String) = "$imageUrl$apiKey&i=$id"


    return this.map { dto ->
        TrendingMovie(
            title = dto.movie.title ?: "",
            genre = (dto.movie.genres?.firstOrNull() ?: "").capitalize(Locale.current),
            certification = dto.movie.certification ?: "",
            image = movieIdToImage(dto.movie.ids.imdb ?: ""),
            stars = ((dto.movie.rating?.toInt() ?: 0) / 2),
            duration = durationToString(dto.movie.runtime ?: 0),
        )
    }
}


fun AnticipatedMovieDto.toTrendingMovie(): AnticipatedMovie {
    TODO()
}