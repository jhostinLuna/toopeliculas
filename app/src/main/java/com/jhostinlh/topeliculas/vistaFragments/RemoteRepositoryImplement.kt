package com.jhostinlh.topeliculas.vistaFragments

import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.modelo.retrofit.MyApiService
import com.jhostinlh.topeliculas.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.core.platform.NetworkHandler
import com.jhostinlh.topeliculas.core.platform.request
import com.jhostinlh.topeliculas.modelo.dao.PelisDao
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.ObjMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImplement @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val myApiService: MyApiService,
    private val pelisDao: PelisDao
): RemoteRepository {
    override fun getListMovies(nameList: String): Either<Failure, ObjMovies> {
        return when(networkHandler.isNetworkAvailable()) {
            true -> request(
                myApiService.getListMovies(value = nameList),
                {
                    it
                },
                ObjMovies()
            )
            false, null -> Either.Left(Failure.NetworkConnection())
        }
    }

    override fun searchMovies(query: String): Either<Failure, ObjMovies> {
        return when(networkHandler.isNetworkAvailable()){
            true -> request(
                myApiService.buscarPeli(query = query),
                {
                    it
                },
                ObjMovies()
            )
            false, null -> Either.Left(Failure.NetworkConnection())
        }
    }

    override fun getListTrailer(idMovie: Int): Either<Failure, Trailer> {
        return when(networkHandler.isNetworkAvailable()){
            true -> request(
                myApiService.getVideos(movie_id = idMovie),
                {
                    it
                },
                Trailer()
            )
            false, null -> Either.Left(Failure.NetworkConnection())

        }
    }

    override fun getLocalFavorites(): Flow<List<Pelicula>> = pelisDao.getFavoritos()


    //  Para acceder al Flow con metodo collect
    //var repoTopRated: Flow<List<Pelicula>>? = pelisDao.getAll()
    //val repoFavorite: Flow<List<Pelicula>> = pelisDao.getAll()


    override  fun findPeliById(id: Int): Flow<Pelicula> =
        pelisDao.findPeliById(id)


    override fun updatePeli(peli: Pelicula) =
        pelisDao.updatePeli(peli)

    override fun deletePeli(peli: Pelicula) =
        pelisDao.deletePeli(peli)

    override fun insertPeli(peli: Pelicula) =
        pelisDao.insertPeli(peli)

}