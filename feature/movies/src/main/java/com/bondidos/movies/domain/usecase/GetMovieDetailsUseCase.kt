package com.bondidos.movies.domain.usecase

import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseError
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import com.bondidos.movies.domain.model.MovieDetails
import com.bondidos.movies.domain.repository.TraktApiRepository
import com.bondidos.movies.domain.usecase.models.GetMovieDetailsParams
import com.bondidos.ui.composables.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val traktApiRepository: TraktApiRepository,
) : BaseUseCase<GetMovieDetailsParams, Flow<UseCaseResult<MovieDetails>>>() {
    override fun invoke(params: GetMovieDetailsParams): Flow<UseCaseResult<MovieDetails>> {
        return when (params.type) {
            MovieType.Trending -> traktApiRepository.getTrendingMovieDetails(
                params.traktId,
                params.page
            )

            MovieType.Anticipated -> traktApiRepository.getAnticipatedMovieDetails(
                params.traktId,
                params.page
            )
        }
            .map(::toUseCaseResult)
            .catch { emit(UseCaseResult.Error(it.toUseCaseError())) }
    }

    private fun toUseCaseResult(movieDetails: MovieDetails?) = movieDetails?.let {
        UseCaseResult.Success(it)
    } ?: UseCaseResult.Error(UseCaseError(message = "Can't find Movie"))
}