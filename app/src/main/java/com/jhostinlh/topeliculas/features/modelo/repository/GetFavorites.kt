package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.news.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetFavorites(private val moviesRepository: MoviesRepository): UseCase<Flow<List<Pelicula>>, GetFavorites.Params>() {
    override suspend fun run(params: Params) = moviesRepository.getFavoritesListMovies()
    class Params
}