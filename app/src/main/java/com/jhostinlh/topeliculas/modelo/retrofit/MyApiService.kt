package com.jhostinlh.topeliculas.modelo.retrofit


import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.ObjMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApiService {
    @GET("movie/{nameList}?&append_to_response=videos")
    fun getListMovies(@Path(value ="nameList" ) value:String,
                      @Query("api_key") api_key: String = Data.API_KEY,
                      @Query("language") idioma: String = Data.LENGUAGE): Call<ObjMovies>

    @GET("movie/{id}/videos")
    fun getVideos(@Path(value="id") movie_id: Int,
                  @Query("api_key") api_key: String = Data.API_KEY,
                  @Query("language") idioma: String = Data.LENGUAGE): Call<Trailer>
    @GET("search/movie")
    fun buscarPeli(
        @Query("page") page: Int = 1,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("query") query: String,
        @Query("api_key") api_key: String = Data.API_KEY,
        @Query("language") idioma: String = Data.LENGUAGE): Call<ObjMovies>
}