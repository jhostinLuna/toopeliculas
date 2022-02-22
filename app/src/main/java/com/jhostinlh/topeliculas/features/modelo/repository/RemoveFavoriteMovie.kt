package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.news.MoviesRepository

class RemoveFavoriteMovie(private val moviesRepository: MoviesRepository): UseCase<Any, RemoveFavoriteMovie.Params>() {
    override suspend fun run(params: Params) = moviesRepository.remove(params.movie)
    class Params(val movie: Movie)
}