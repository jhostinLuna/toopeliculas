package com.jhostinlh.topeliculas.vistaFragments

import com.jhostinlh.topeliculas.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.ObjMovies
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getListMovies(nameList: String): Either<Failure, ObjMovies>
    fun searchMovies(query:String): Either<Failure, ObjMovies>
    fun getListTrailer(idMovie: Int): Either<Failure, Trailer>
    fun getLocalFavorites(): Flow<List<Pelicula>>
    fun findPeliById(id: Int): Flow<Pelicula>

    fun updatePeli(peli: Pelicula): Either<Failure,Unit>

    fun deletePeli(peli: Pelicula): Either<Failure,Unit>

    fun insertPeli(peli: Pelicula): Either<Failure,Unit>
}