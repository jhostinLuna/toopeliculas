package com.jhostinlh.topeliculas.modelo.Entitys


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val resultTrailers: List<ResultTrailer>
)