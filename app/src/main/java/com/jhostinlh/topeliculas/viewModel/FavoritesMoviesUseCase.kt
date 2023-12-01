package com.jhostinlh.topeliculas.viewModel

import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.vistaFragments.RemoteRepository
import javax.inject.Inject

class FavoritesMoviesUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
): UseCase<Unit, FavoritesMoviesUseCase.Params>() {
    companion object {
        const val ADD = "add"
        const val DELETE = "delete"
        const val UPDATE = "update"
    }
    class Params(
        val operation: String,
        val favoriteMovie: Pelicula
    )

    override suspend fun run(params: Params): Either<Failure, Unit> {
        return when(params.operation){
            ADD -> remoteRepository.insertPeli(peli = params.favoriteMovie)
            DELETE -> remoteRepository.deletePeli(peli = params.favoriteMovie)
            UPDATE -> remoteRepository.updatePeli(peli = params.favoriteMovie)
            else -> Either.Left(Failure.CustomError(-100,"Error, se ha pasado argumento invalido al operar con local storage"))
        }
    }
}