package com.jhostinlh.topeliculas.modelo.repository

import com.jhostinlh.topeliculas.modelo.Entitys.Pelicula

interface PelisRepository {
    suspend fun findPeliById(id: Int): Pelicula
    suspend fun updatePeli(peli: Pelicula)
    suspend fun deletePeli(peli: Pelicula)
    suspend fun insertPeli(peli: Pelicula)

}