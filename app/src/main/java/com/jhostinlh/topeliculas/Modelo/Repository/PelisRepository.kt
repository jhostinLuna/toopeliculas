package com.jhostinlh.topeliculas.Modelo.Repository

import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula

interface PelisRepository {
    suspend fun findPeliById(id: Int): Pelicula
    suspend fun updatePeli(peli: Pelicula)
    suspend fun deletePeli(peli: Pelicula)
    suspend fun insertPeli(peli: Pelicula)

}