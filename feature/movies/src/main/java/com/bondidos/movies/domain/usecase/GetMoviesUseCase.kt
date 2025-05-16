package com.bondidos.movies.domain.usecase

import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseError
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import com.bondidos.exceptions.CacheExceptions
import com.bondidos.movies.domain.model.movie.Movie
import com.bondidos.movies.domain.repository.TraktApiRepository
import com.bondidos.movies.domain.usecase.models.GetMoviesParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val traktApiRepository: TraktApiRepository,
) : BaseUseCase<GetMoviesParams, Flow<UseCaseResult<List<Movie>>>>() {
    override fun invoke(params: GetMoviesParams): Flow<UseCaseResult<List<Movie>>> {
        return when (params) {
            is GetMoviesParams.GetAnticipated -> traktApiRepository.getAnticipated(params.page)
            is GetMoviesParams.GetTrending -> traktApiRepository.getTrending(params.page)
        }
            .map(::toUseCaseResult)
            .catch { throwable ->
                when (throwable) {
                    is IOException -> {
                        emitAll(handleGetFromCacheIgnoreLiveTime(params))
                    }
                    else -> emit(UseCaseResult.Error(throwable.toUseCaseError()))
                }
            }
    }

    private fun toUseCaseResult(movies: List<Movie>) = if (movies.isEmpty())
        UseCaseResult.Error(UseCaseError(message = "Movies is Empty")) else UseCaseResult.Success(
        movies
    )

    private fun handleGetFromCacheIgnoreLiveTime(params: GetMoviesParams)
            : Flow<UseCaseResult<List<Movie>>> {
        return when (params) {
            is GetMoviesParams.GetAnticipated -> traktApiRepository
                .getAnticipatedFromCache(params.page)

            is GetMoviesParams.GetTrending -> traktApiRepository.getTrendingFromCache(
                params.page
            )
        }
            .map(::toUseCaseResult)
            .catch {
                when (it) {
                    is CacheExceptions -> emit(UseCaseResult.Error(it.toUseCaseError()))
                }
            }
    }
}