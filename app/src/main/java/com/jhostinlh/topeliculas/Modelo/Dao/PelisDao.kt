package com.jhostinlh.topeliculas.Modelo.Dao

import androidx.room.*
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import kotlinx.coroutines.flow.Flow

@Dao
interface PelisDao {
    @Query("select * from Pelicula where favorito = 1")
    fun getFavoritos(): Flow<List<Pelicula>>

    @Query("select * from Pelicula ")
    fun getAll(): Flow<List<Pelicula>>

    @Query("select * from Pelicula where id = :id")
    suspend fun findPeliById(id: Int): Pelicula

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPeli(peli: Pelicula)

    @Update
    suspend fun updatePeli(peli: Pelicula)

    @Delete
    fun deletePeli(peli: Pelicula)
}