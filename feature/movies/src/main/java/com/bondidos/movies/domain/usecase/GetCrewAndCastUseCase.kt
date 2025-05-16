package com.bondidos.movies.domain.usecase

import com.bondidos.base.BaseUseCase
import com.bondidos.base.UseCaseResult
import com.bondidos.base.toUseCaseError
import com.bondidos.movies.domain.model.people.PeopleWithImage
import com.bondidos.movies.domain.repository.TmdbRepository
import com.bondidos.movies.domain.repository.TraktApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCrewAndCastUseCase @Inject constructor(
    private val traktApiRepository: TraktApiRepository,
    private val tmdbRepository: TmdbRepository,
) : BaseUseCase<Int, Flow<UseCaseResult<List<PeopleWithImage>>>>() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(params: Int): Flow<UseCaseResult<List<PeopleWithImage>>> {

        return traktApiRepository.getCastAndCrew(params)
            .flatMapConcat { list ->
                val imageFlow = list.map { castAndCrewItem ->
                    val id = castAndCrewItem.person?.ids?.tmdb
                    if (id == null) flow {}
                    else tmdbRepository.fetchImage(id).map { image ->
                        PeopleWithImage(
                            activity = castAndCrewItem.activity,
                            activities = castAndCrewItem.activities,
                            person = castAndCrewItem.person,
                            imageUrl = image.filePath!!,
                        )
                    }
                }
                combine(imageFlow) {
                    if (it.isEmpty()) UseCaseResult.Error(
                        Exception("Empty crew and cast").toUseCaseError()
                    ) else UseCaseResult.Success(it.toList())
                }
            }.catch {
                emit(UseCaseResult.Error(it.toUseCaseError()))
            }
    }
}