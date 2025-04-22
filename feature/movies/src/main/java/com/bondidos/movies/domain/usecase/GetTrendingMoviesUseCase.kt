package com.bondidos.movies.domain.usecase

import com.bondidos.base.BaseUseCase
import com.bondidos.movies.domain.model.TrendingMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    //todo repo + mapper
) : BaseUseCase<Int, Flow<List<TrendingMovie>>>() {
    override fun invoke(params: Int): Flow<List<TrendingMovie>> {
        TODO("Not yet implemented")
    }
}