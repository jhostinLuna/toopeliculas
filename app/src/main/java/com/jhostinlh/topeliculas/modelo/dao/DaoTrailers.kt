package com.jhostinlh.topeliculas.modelo.dao

import androidx.room.*
import com.jhostinlh.topeliculas.modelo.Entitys.ResultTrailer

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