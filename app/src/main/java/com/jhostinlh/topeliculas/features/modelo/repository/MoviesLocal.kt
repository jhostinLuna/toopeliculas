package com.jhostinlh.topeliculas.features.news
import com.jhostinlh.topeliculas.core.dataBase.AppDataBase
import com.jhostinlh.topeliculas.core.platform.ContextHandler
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.repository.MoviesDBLocal
import kotlinx.coroutines.flow.Flow

class MoviesLocal
(contextHandler: ContextHandler): MoviesDBLocal{

    private val moviesApi by lazy {
        AppDataBase.getInstance(contextHandler.appContext).pelisDao()
    }

    override fun getFavorites(): Flow<List<Pelicula>> = moviesApi.getFavoritos()

    override fun getAll(): Flow<List<Pelicula>> = moviesApi.getAll()

    override fun findPeliById(id: Int): Flow<Pelicula> = moviesApi.findPeliById(id)

    override fun insertPeli(peli: Pelicula) = moviesApi.insertPeli(peli)

    override fun updatePeli(peli: Pelicula) = moviesApi.insertPeli(peli)

    override fun deletePeli(peli: Pelicula) = moviesApi.deletePeli(peli)


}