package com.jhostinlh.topeliculas.vistaFragments

import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.modelo.entitys.Trailer
import javax.inject.Inject

class GetTrailersUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
):UseCase<Trailer,GetTrailersUseCase.Params>() {
    class Params(
        val idMovie: Int
    )

    override suspend fun run(params: Params): Either<Failure, Trailer> = remoteRepository.getListTrailer(params.idMovie)
}