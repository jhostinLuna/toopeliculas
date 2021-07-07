package com.jhostinlh.topeliculas.Modelo.Repository

import com.jhostinlh.topeliculas.Modelo.Dao.DaoTrailers
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer

class ImplementTrailerRepository(val daoTrailers: DaoTrailers): TrailerRepository {
    override suspend fun findTrailerById(id: Int): ResultTrailer {
        return daoTrailers.findTrailerById(id)
    }

    override suspend fun insertTrailer(trailer: ResultTrailer) {
        daoTrailers.insertTrailer(trailer)
    }

    override suspend fun updateTrailer(trailer: ResultTrailer) {
        daoTrailers.updateTrailer(trailer)
    }

    override fun deleteTrailer(trailer: ResultTrailer) {
        daoTrailers.deleteTrailer(trailer)
    }


}