package com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.generos


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)