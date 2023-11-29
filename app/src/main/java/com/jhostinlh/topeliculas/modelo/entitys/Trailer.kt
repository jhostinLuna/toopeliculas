package com.jhostinlh.topeliculas.modelo.entitys


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("results")
    val resultTrailers: List<ResultTrailer> = emptyList()
)