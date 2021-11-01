package com.jhostinlh.topeliculas.modelo.dataRemote


import com.google.gson.annotations.SerializedName

data class ListLatesMovies(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)