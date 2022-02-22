package com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.generos


import com.google.gson.annotations.SerializedName

data class generos(
    @SerializedName("genres")
    val genres: List<Genre>
)