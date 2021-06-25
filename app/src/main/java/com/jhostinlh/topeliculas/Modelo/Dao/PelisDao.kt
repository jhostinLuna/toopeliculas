package com.jhostinlh.topeliculas.Modelo.Dao

import androidx.room.*
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
@Dao
interface PelisDao {
    @Query("select * from Pelicula ")
    suspend fun getAll(): List<Pelicula>

    @Query("select * from Pelicula where id = :id")
    suspend fun findPeliById(id: Int): Pelicula

    @Insert
    suspend fun insertPeli(peli: Pelicula)

    @Update
    suspend fun updatePeli(peli: Pelicula)

    @Delete
    fun deletePeli(peli: Pelicula)
}