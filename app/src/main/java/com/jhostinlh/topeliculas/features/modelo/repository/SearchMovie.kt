package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.news.MoviesRepository

class SearchMovie(private val moviesRepository: MoviesRepository): UseCase<List<Movie>, SearchMovie.Params>() {
    override suspend fun run(params: Params) = moviesRepository.searchMovie(params.query)
    class Params(val query:String)
}