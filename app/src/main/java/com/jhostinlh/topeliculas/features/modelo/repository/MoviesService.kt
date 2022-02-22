package com.jhostinlh.topeliculas.features.news

import com.jhostinlh.topeliculas.features.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.features.modelo.repository.MoviesApi
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.ObjMovies
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
class MoviesService
(retrofit: Retrofit) : MoviesApi {
    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    override fun getListMovies(value: String, api_key: String, idioma: String): Call<ObjMovies> = moviesApi.getListMovies(value,api_key,idioma)

    override fun getVideos(movie_id: Int, api_key: String, idioma: String): Call<Trailer> = moviesApi.getVideos(movie_id,api_key,idioma)

    override fun buscarPeli(
        page: Int,
        include_adult: Boolean,
        query: String,
        api_key: String,
        idioma: String
    ): Call<ObjMovies> = moviesApi.buscarPeli(page,include_adult,query,api_key,idioma)
}