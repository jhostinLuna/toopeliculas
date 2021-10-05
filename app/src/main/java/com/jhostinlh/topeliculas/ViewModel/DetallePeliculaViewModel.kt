package com.jhostinlh.topeliculas.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Dao.DaoTrailers
import com.jhostinlh.topeliculas.Modelo.Database.AppDataBase
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementTrailerRepository
import com.jhostinlh.topeliculas.Modelo.Repository.TrailerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetallePeliculaViewModel(val repository: ImplementPelisRepository,val pelicula: Pelicula) : ViewModel() {
    private val peli = pelicula
    private val listTrailer: MutableLiveData<List<ResultTrailer>> by lazy {
        MutableLiveData<List<ResultTrailer>>().also {
            loadTrailers(peli.id)
        }
    }


    fun getListTrailer(): LiveData<List<ResultTrailer>>{
        return listTrailer
    }

    private fun loadTrailers(idPelicula: Int) {

        viewModelScope.launch(Dispatchers.IO){

            listTrailer.postValue(repository.getListTrailers(idPelicula)!!.resultTrailers)
        }


    }

}