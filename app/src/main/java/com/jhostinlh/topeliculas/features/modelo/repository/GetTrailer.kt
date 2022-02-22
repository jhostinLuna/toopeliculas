package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.core.interactor.UseCase
import com.jhostinlh.topeliculas.features.modelo.entitys.ResultTrailer
import com.jhostinlh.topeliculas.features.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.news.MoviesRepository

class GetTrailer(private val moviesRepository: MoviesRepository): UseCase<Trailer, GetTrailer.Params>() {
    override suspend fun run(params: Params) = moviesRepository.getTrailer(params.idMovie,params.language)
    class Params(val idMovie:Int,val language:String)
}