package com.bondidos.movies.presentation.movie_details_screen.model

import com.bondidos.movies.presentation.movie_details_screen.intent.CrewAndCastUI
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEffect
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEvent
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsState
import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class MovieDetailsReducer @Inject constructor() :
    Reducer<MovieDetailsState, MovieDetailsEvent, MovieDetailsEffect> {
    override fun reduce(
        previousState: MovieDetailsState,
        event: MovieDetailsEvent
    ): Pair<MovieDetailsState, MovieDetailsEffect?> {
        return when (event) {
            is MovieDetailsEvent.Loading -> previousState.copy(isLoading = true) to null
            is MovieDetailsEvent.HandleError -> previousState to MovieDetailsEffect.ShowErrorMessage(
                event.message
            )

            is MovieDetailsEvent.DetailsLoaded -> previousState.copy(
                isLoading = false,
                title = event.moveDetails.title,
                overview = event.moveDetails.overview,
                durationAndCertification = event.moveDetails.durationAndCertification,
                genres = event.moveDetails.genres,
                image = event.moveDetails.image,
                stars = event.moveDetails.stars,
                rating = event.moveDetails.rating,
                id = event.moveDetails.id,
            ) to null

            is MovieDetailsEvent.ChangeDetailsType -> previousState.copy(
                detailsType = event.type
            ) to null

            is MovieDetailsEvent.CrewAndCastLoaded -> previousState.copy(
                crewAndCast = event.crewAndCast.map {
                    CrewAndCastUI(
                        it.imageUrl,
                        it.person?.name ?: "",
                        it.activities?.firstOrNull() ?: ""
                    )
                }
            ) to null

            MovieDetailsEvent.Loaded -> previousState.copy(isLoading = false) to null
        }
    }
}