package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.ObjMovies
import kotlinx.coroutines.flow.Flow

interface PelisRepository {
    suspend fun findPeliById(id: Int): Flow<Pelicula>
    suspend fun updatePeli(peli: Pelicula)
    suspend fun deletePeli(peli: Pelicula)
    suspend fun insertPeli(peli: Pelicula)
    suspend fun buscarPeli(query:String): ObjMovies?
}