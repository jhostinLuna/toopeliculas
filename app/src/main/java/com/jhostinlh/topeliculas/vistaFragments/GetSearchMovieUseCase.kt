package com.jhostinlh.topeliculas.vistaFragments

import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.ObjMovies
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
): UseCase<ObjMovies, GetSearchMovieUseCase.Params>() {
    class Params(
        val query: String
    )

    override suspend fun run(params: Params): Either<Failure, ObjMovies> = remoteRepository.searchMovies(params.query)
}