package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import kotlinx.coroutines.flow.Flow

interface MoviesDBLocal {
    fun getFavorites(): Flow<List<Pelicula>>
    fun getAll(): Flow<List<Pelicula>>
    fun findPeliById(id: Int): Flow<Pelicula>
    fun insertPeli(peli: Pelicula)
    fun updatePeli(peli: Pelicula)
    fun deletePeli(peli: Pelicula)
}