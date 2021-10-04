package com.jhostinlh.topeliculas.VistaFragments.DetallePelicula

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Dao.DaoTrailers
import com.jhostinlh.topeliculas.Modelo.Database.AppDataBase
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementTrailerRepository
import com.jhostinlh.topeliculas.Modelo.Repository.TrailerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetallePeliculaViewModel(application: Application, pelicula: Pelicula) : AndroidViewModel(application) {
    private val peli = pelicula
    private val listTrailer: MutableLiveData<List<ResultTrailer>> by lazy {
        MutableLiveData<List<ResultTrailer>>().also {
            loadVideo(peli.id)
        }
    }


    val context = getApplication<Application>().applicationContext
    val apiService: MyApiService? = MyApiAdapter.getApiService()
    val database = AppDataBase.getInstance(context)

    fun getListTrailer(): LiveData<List<ResultTrailer>>{
        return listTrailer
    }

    private fun loadVideo(idPelicula: Int) {

        val trailersDao: DaoTrailers = database?.TrailerDao()
        val repository: TrailerRepository = ImplementTrailerRepository(trailersDao)

        viewModelScope.launch {
            val a = withContext(Dispatchers.IO){
                val peliculas = repository.findTrailerById(idPelicula)
                Log.i("videos","trailers :"+peliculas.toString())
                if (peliculas != null) {
                    val listaTrail: List<ResultTrailer> = listOf(peliculas)
                    listTrailer.postValue(listaTrail)

                }else{
                    val call = apiService?.getVideos(idPelicula, Data.API_KEY, Data.LENGUAGE)?.execute()
                    val objectVideo = call?.body()

                    if (objectVideo != null) {
                        listTrailer.postValue(objectVideo.resultTrailers)
                    }
                    Log.i("trailers","esto?: ${objectVideo.toString()}")
                    if (objectVideo != null) {
                        for (trailer in objectVideo.resultTrailers){
                            trailer.idPeli = objectVideo.id
                            repository.insertTrailer(trailer)
                        }
                    }
                }
            }

        }


    }

}