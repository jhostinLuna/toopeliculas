package com.jhostinlh.topeliculas.viewModel

import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.vistaFragments.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesMoviesUseCase @Inject constructor(
    val repository: RemoteRepository
): UseCase<Flow<List<Pelicula>>, GetFavoritesMoviesUseCase.Params>() {
    class Params

    override suspend fun run(params: Params): Either<Failure, Flow<List<Pelicula>>> =
        Either.Right(repository.getLocalFavorites())
}