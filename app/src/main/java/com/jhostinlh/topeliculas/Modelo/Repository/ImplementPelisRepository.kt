package com.jhostinlh.topeliculas.Modelo.Repository

import android.util.Log
import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Dao.PelisDao
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.TopRated
import com.jhostinlh.topeliculas.Modelo.Entitys.Trailer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

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