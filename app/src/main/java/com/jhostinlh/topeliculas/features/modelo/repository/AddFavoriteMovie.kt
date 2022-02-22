package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.news.MoviesRepository

class AddFavoriteMovie(private val moviesRepository: MoviesRepository): UseCase<Any, AddFavoriteMovie.Params>() {
    override suspend fun run(params: Params) = moviesRepository.add(params.movie)
    class Params(val movie:Movie)
}