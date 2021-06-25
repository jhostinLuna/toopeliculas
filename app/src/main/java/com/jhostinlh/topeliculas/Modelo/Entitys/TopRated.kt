package com.jhostinlh.topeliculas.Modelo.Entitys


import com.google.gson.annotations.SerializedName

data class TopRated(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Pelicula>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)