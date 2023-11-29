package com.jhostinlh.topeliculas.modelo.retrofit.dataRemote


import com.google.gson.annotations.SerializedName

data class ObjMovies(
    @SerializedName("dates")
    val dates: Dates = Dates(),
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<Movie> = emptyList(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)