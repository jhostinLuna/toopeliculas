package com.jhostinlh.topeliculas.features.modelo.repository

import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.core.dao.PelisDao
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.ObjMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

data class ImplementPelisRepository(val pelisDao: PelisDao, val apiService: MyApiService): PelisRepository {

//  Para acceder al Flow con metodo collect
    //var repoTopRated: Flow<List<Pelicula>>? = pelisDao.getAll()
    val repoFavorite: Flow<List<Pelicula>> = pelisDao.getAll()


    override suspend fun findPeliById(id: Int): Flow<Pelicula> = withContext(Dispatchers.IO){
        pelisDao.findPeliById(id)
    }

    override suspend fun updatePeli(peli: Pelicula) = withContext(Dispatchers.IO){
        pelisDao.updatePeli(peli)

    }

    override suspend fun deletePeli(peli: Pelicula) = withContext(Dispatchers.IO){
        pelisDao.deletePeli(peli)
    }

    override suspend fun insertPeli(peli: Pelicula) = withContext(Dispatchers.IO){
        pelisDao.insertPeli(peli)
    }

    override suspend fun buscarPeli(query: String): ObjMovies? = withContext(Dispatchers.IO){
        apiService.buscarPeli(1,false,query,Data.API_KEY,Data.LENGUAGE).execute().body()
    }

    fun getListMovies(nameLista:String): ObjMovies? {
        return apiService.getTopRated(nameLista,Data.API_KEY, Data.LENGUAGE).execute().body()

    }



    suspend fun getListTrailers(idPeli: Int): Trailer? = withContext(Dispatchers.IO){

        apiService.getVideos(idPeli,Data.API_KEY,Data.LENGUAGE).execute().body()

    }



}