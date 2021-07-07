package com.jhostinlh.topeliculas.Modelo.Repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer

interface TrailerRepository {
    suspend fun findTrailerById(id: Int): ResultTrailer

    suspend fun insertTrailer(trailer: ResultTrailer)

    suspend fun updateTrailer(trailer: ResultTrailer)

    fun deleteTrailer(trailer: ResultTrailer)
}