package com.jhostinlh.topeliculas.modelo.repository

import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.modelo.dao.PelisDao
import com.jhostinlh.topeliculas.modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.Entitys.TopRated
import com.jhostinlh.topeliculas.modelo.Entitys.Trailer
import kotlinx.coroutines.flow.Flow

data class ImplementPelisRepository(val pelisDao: PelisDao,val apiService: MyApiService): PelisRepository {

//  Para acceder al Flow con metodo collect
    //var repoTopRated: Flow<List<Pelicula>>? = pelisDao.getAll()
    val repoFavorite: Flow<List<Pelicula>> = pelisDao.getFavoritos()


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
    suspend fun actualizarTopRated(nameLista:String): TopRated? {
        return apiService.topRated(nameLista,Data.API_KEY, Data.LENGUAGE)?.execute().body()

    }



    suspend fun getListTrailers(idPeli: Int): Trailer?{
        return apiService.getVideos(idPeli,Data.API_KEY,Data.LENGUAGE).execute().body()

    }



}