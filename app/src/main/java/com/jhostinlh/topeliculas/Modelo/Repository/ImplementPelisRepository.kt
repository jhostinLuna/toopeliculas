package com.jhostinlh.topeliculas.Modelo.Repository

import com.jhostinlh.topeliculas.Modelo.Dao.PelisDao
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula

class ImplementPelisRepository(val pelisDao: PelisDao): PelisRepository {



    override suspend fun getAllPelis(): List<Pelicula> {
        return pelisDao.getAll()
    }

    override suspend fun findPeliById(id: Int): Pelicula {
        return pelisDao.findPeliById(id)
    }

    override suspend fun updatePeli(peli: Pelicula) {
        pelisDao.updatePeli(peli)
    }

    override suspend fun deletePeli(peli: Pelicula) {
        pelisDao.deletePeli(peli)
    }

    override suspend fun insertPeli(peli: Pelicula) {
        pelisDao.insertPeli(peli)
    }
}