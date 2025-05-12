package com.bondidos.movies.data.extensions

import android.annotation.SuppressLint
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.bondidos.movies.domain.model.movie.Movie
import com.bondidos.movies.domain.model.movie.MovieDetails
import com.bondidos.network.dto.movies.anticipated.AnticipatedMovieDto
import com.bondidos.network.dto.movies.trending.TrendingMovieDto
import javax.inject.Inject
import javax.inject.Named

private const val imageUrl = "https://img.omdbapi.com/?apikey="

class MovieDtoConverter @Inject constructor(
    @Named("OMDB_API") private val omdbApiKey: String
) {
    fun toTrendingMovie(data: List<TrendingMovieDto>): List<Movie> {
        return data.map { dto ->
            Movie(
                id = dto.movie.ids.trakt,
                title = dto.movie.title ?: "",
                genre = (dto.movie.genres?.firstOrNull() ?: "").capitalize(Locale.current),
                certification = dto.movie.certification ?: "",
                image = movieIdToImage(dto.movie.ids.imdb ?: ""),
                stars = ratingToStarsCount(dto.movie.rating),
                duration = durationToString(dto.movie.runtime ?: 0),
            )
        }
    }

    fun toAnticipatedMovie(data: List<AnticipatedMovieDto>): List<Movie> {
        return data.map { dto ->
            Movie(
                id = dto.movie.ids.trakt,
                title = dto.movie.title ?: "",
                genre = (dto.movie.genres?.firstOrNull() ?: "").capitalize(Locale.current),
                certification = dto.movie.certification ?: "",
                image = movieIdToImage(dto.movie.ids.imdb ?: ""),
                stars = ratingToStarsCount(dto.movie.rating),
                duration = durationToString(dto.movie.runtime ?: 0),
            )
        }
    }

    fun toMovieDetails(data: TrendingMovieDto?) = MovieDetails(
        title = data?.movie?.title ?: "",
        overview = data?.movie?.overview ?: "",
        durationAndCertification = durationToString(
            data?.movie?.runtime ?: 0
        ) + " | ${data?.movie?.certification}",
        genres = data?.movie?.genres?.joinToString(separator = ", ") { it.capitalize(Locale.current) }
            ?: "",
        image = movieIdToImage(data?.movie?.ids?.imdb ?: ""),
        stars = ratingToStarsCount(data?.movie?.rating),
        rating = ratingToString(data?.movie?.rating),
        id = data?.movie?.ids?.trakt ?: 0,
        trailer = data?.movie?.trailer ?: ""
    )

    fun toMovieDetails(data: AnticipatedMovieDto?) = MovieDetails(
        title = data?.movie?.title ?: "",
        overview = data?.movie?.overview ?: "",
        durationAndCertification = durationToString(
            data?.movie?.runtime ?: 0
        ) + " | ${data?.movie?.certification}",
        genres = data?.movie?.genres?.joinToString(separator = ", ") { it.capitalize(Locale.current) }
            ?: "",
        image = movieIdToImage(data?.movie?.ids?.imdb ?: ""),
        stars = ratingToStarsCount(data?.movie?.rating),
        rating = ratingToString(data?.movie?.rating),
        id = data?.movie?.ids?.trakt ?: 0,
        trailer = data?.movie?.trailer ?: ""
    )

    private fun ratingToStarsCount(rating: Double?) = ((rating?.toInt() ?: 0) / 2)

    @SuppressLint("DefaultLocale")
    private fun ratingToString(rating: Double?) = String.format("%.1f", rating?.div(2)) + "/5"

    private fun durationToString(params: Int): String {
        val hours = params / 60
        val minutes = params - hours * 60
        if (hours == 0) return " ${minutes}m"
        return " ${hours}h ${minutes}m"
    }

    private fun movieIdToImage(id: String) = "$imageUrl$omdbApiKey&i=$id"
}
