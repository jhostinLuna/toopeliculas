package com.jhostinlh.topeliculas.features.news

import android.content.SharedPreferences
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.functional.Either
import com.jhostinlh.topeliculas.core.platform.NetworkHandler
import com.jhostinlh.topeliculas.core.platform.ServiceKOs
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.entitys.ResultTrailer
import com.jhostinlh.topeliculas.features.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.ObjMovies
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface MoviesRepository {
    fun searchMovie(query:String): Either<Failure, List<Movie>>
    fun getFavoritesListMovies(): Either<Failure, Flow<List<Pelicula>>>
    fun getRemoteListMovies(value: String): Either<Failure, List<Movie>>
    fun add(movie: Movie): Either<Failure, Any>
    fun remove(movie: Movie): Either<Failure, Any>
    fun getTrailer(idMovie:Int,language:String): Either<Failure, Trailer>
    class Network
    (private val networkHandler: NetworkHandler,
     private val service: MoviesService,
     private val local: MoviesLocal): MoviesRepository {

        override fun getRemoteListMovies(nameLista:String): Either<Failure, List<Movie>> {
            return when(networkHandler.isConnected) {
                true -> request(
                        service.getListMovies(nameLista, Data.API_KEY,Data.LENGUAGE),
                        {
                            it.results

                        },
                        ObjMovies(null,0,emptyList(), 0, 0)
                    )
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        override fun searchMovie(query: String): Either<Failure, List<Movie>> {
            return when(networkHandler.isConnected){
                true -> request(
                    service.buscarPeli(1,false,query,Data.API_KEY,Data.LENGUAGE),
                    {
                        it.results
                    },
                    ObjMovies(null,0,emptyList(), 0, 0)
                )
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        override fun getFavoritesListMovies(): Either<Failure, Flow<List<Pelicula>>> {
            return try {
                val movies = local.getFavorites()

                    Either.Right(movies)

            }catch (e: Exception) {
                Either.Left(Failure.CustomError(ServiceKOs.DATABASE_ACCESS_ERROR, e.message))
            }
        }

        override fun add(movie: Movie): Either<Failure, Any> {
            return try {
                Either.Right(local.insertPeli(movie.toPeliculaEntity()))
            } catch (e: Exception) {
                Either.Left(Failure.CustomError(ServiceKOs.DATABASE_ACCESS_ERROR, e.message))
            }
        }

        override fun remove(movie: Movie):Either<Failure, Any> {

            return try {
                Either.Right(local.deletePeli(movie.toPeliculaEntity()))
            } catch (e: Exception) {
                Either.Left(Failure.CustomError(ServiceKOs.DATABASE_ACCESS_ERROR, e.message))
            }
        }

        override fun getTrailer(idMovie: Int,language: String): Either<Failure, Trailer> {
            return when(networkHandler.isConnected){
                true -> request(
                    service.getVideos(idMovie,Data.API_KEY,language),
                    {trailer ->
                        trailer
                    },
                    Trailer(0, emptyList())
                )
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError())
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError())
            }
        }
    }
}