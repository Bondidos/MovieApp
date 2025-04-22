package com.bondidos.movies.domain.usecase

import com.bondidos.base.BaseUseCase
import com.bondidos.movies.domain.model.AnticipatedMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnticipatedMovieUseCase @Inject constructor(): BaseUseCase<Int, Flow<List<AnticipatedMovie>>>() {
    override fun invoke(params: Int): Flow<List<AnticipatedMovie>> {
        TODO("Not yet implemented")
    }
}