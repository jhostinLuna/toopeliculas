package com.jhostinlh.topeliculas.features.modelo.entitys


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val resultTrailers: List<ResultTrailer>
)