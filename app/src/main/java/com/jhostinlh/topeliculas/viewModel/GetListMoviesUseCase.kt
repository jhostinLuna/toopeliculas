package com.jhostinlh.topeliculas.viewModel

import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.ObjMovies
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.vistaFragments.RemoteRepository
import javax.inject.Inject


class GetListMoviesUseCase @Inject constructor(private val remoteRepository: RemoteRepository): UseCase<ObjMovies, GetListMoviesUseCase.Params>() {
    class Params (
        val nameList: String
    )

    override suspend fun run(params: Params): Either<Failure, ObjMovies> = remoteRepository.getListMovies(params.nameList)
}