package com.bondidos.movies.presentation.movie_details_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.ui.composables.MovieDetailsType

@Immutable
data class MovieDetailsState(
    val isLoading: Boolean,
    val title: String,
    val overview: String,
    val durationAndCertification: String,
    val genres: String,
    val image: String,
    val stars: Int,
    val rating: String,
    val id: Int,
    val detailsType: MovieDetailsType,
    val crewAndCast: List<CrewAndCastUI>
) : Reducer.ViewState {

    companion object {
        fun init() = MovieDetailsState(
            isLoading = false,
            title = "",
            overview = "",
            durationAndCertification = "",
            genres = "",
            image = "",
            stars = -1,
            rating = "",
            id = -1,
            detailsType = MovieDetailsType.Detail,
            crewAndCast = emptyList()
        )
    }
}

@Immutable
data class CrewAndCastUI(
    val imageUrl: String,
    val personName: String,
    val role: String,
)