package com.jhostinlh.topeliculas.modelo.repository

import com.jhostinlh.topeliculas.modelo.Entitys.ResultTrailer

interface TrailerRepository {
    suspend fun findTrailerById(id: Int): ResultTrailer

    suspend fun insertTrailer(trailer: ResultTrailer)

    suspend fun updateTrailer(trailer: ResultTrailer)

    fun deleteTrailer(trailer: ResultTrailer)
}