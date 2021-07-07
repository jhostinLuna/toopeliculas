package com.jhostinlh.topeliculas.Modelo.Dao

import androidx.room.*
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer

@Dao
interface DaoTrailers {
    @Query("select * from ResultTrailer where idPeli = :id")
    suspend fun findTrailerById(id: Int): ResultTrailer

    @Insert
    suspend fun insertTrailer(trailer: ResultTrailer)

    @Update
    suspend fun updateTrailer(trailer: ResultTrailer)

    @Delete
    fun deleteTrailer(trailer: ResultTrailer)
}