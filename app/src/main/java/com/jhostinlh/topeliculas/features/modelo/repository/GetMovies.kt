package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.news.MoviesRepository

class GetMovies(private val moviesRepository: MoviesRepository): UseCase<List<Movie>, GetMovies.Params>() {
    override suspend fun run(params: Params) = moviesRepository.getRemoteListMovies(params.nameList)
    class Params(val nameList:String)
}