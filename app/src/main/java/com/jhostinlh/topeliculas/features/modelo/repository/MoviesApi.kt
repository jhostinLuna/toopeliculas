package com.jhostinlh.topeliculas.features.modelo.repository


import com.jhostinlh.topeliculas.features.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.ObjMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MoviesApi {

    @GET("movie/{nameList}?&append_to_response=videos")
    fun getListMovies(@Path(value ="nameList" ) value:String,
                      @Query("api_key") api_key: String,
                      @Query("language") idioma: String): Call<ObjMovies>

    @GET("movie/{id}/videos")
    fun getVideos(@Path(value="id") movie_id: Int,
                  @Query("api_key") api_key: String,
                  @Query("language") idioma: String): Call<Trailer>
    @GET("search/movie")
    fun buscarPeli(
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean,
        @Query("query") query: String,
        @Query("api_key") api_key: String,
        @Query("language") idioma: String): Call<ObjMovies>
}