package com.jhostinlh.topeliculas.modelo.dao

import androidx.room.*
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import kotlinx.coroutines.flow.Flow

@Dao
interface PelisDao {
    @Query("select * from Pelicula where favorito = 1")
    fun getFavoritos(): Flow<List<Pelicula>>

    @Query("select * from Pelicula ")
    fun getAll(): Flow<List<Pelicula>>

    @Query("select * from Pelicula where id = :id")
    fun findPeliById(id: Int): Flow<Pelicula>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPeli(peli: Pelicula)

    @Update
    fun updatePeli(peli: Pelicula)

    @Delete
    fun deletePeli(peli: Pelicula)

}