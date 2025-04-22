package com.bondidos.movies.domain.usecase

import com.bondidos.base.BaseUseCase
import com.bondidos.movies.domain.model.TrendingMovie
import com.bondidos.movies.domain.repository.TraktApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val traktApiRepository: TraktApiRepository,
) : BaseUseCase<Int, Flow<List<TrendingMovie>>>() {
    override fun invoke(params: Int): Flow<List<TrendingMovie>> {
        return traktApiRepository.getTrending(1)//todo hardcoded page
    }
}