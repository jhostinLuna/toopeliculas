package com.jhostinlh.tiempokotlin.Retrofit


import com.jhostinlh.topeliculas.Modelo.Entitys.TopRated
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiService {

    @GET("top_rated")
    fun topRated(@Query("api_key") api_key: String,
                         @Query("language") idioma: String): Call<TopRated>


}