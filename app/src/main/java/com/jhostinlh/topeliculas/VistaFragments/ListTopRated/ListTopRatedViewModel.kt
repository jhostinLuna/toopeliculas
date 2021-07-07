package com.jhostinlh.topeliculas.VistaFragments.ListTopRated

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Dao.PelisDao
import com.jhostinlh.topeliculas.Modelo.Database.AppDataBase
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository
import com.jhostinlh.topeliculas.Modelo.Repository.PelisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListTopRatedViewModel(application: Application) : AndroidViewModel(application) {
    private val listTopRated: MutableLiveData<List<Pelicula>> by lazy {
        MutableLiveData<List<Pelicula>>().also {
            loadTopRated()
        }
    }


    fun getTopRated(): LiveData<List<Pelicula>> {
        return listTopRated
    }
    private val context = getApplication<Application>().applicationContext
    private val database = AppDataBase.getInstance(context)
    private val peliDao: PelisDao = database?.pelisDao()
    private val repository: PelisRepository = ImplementPelisRepository(peliDao)
    private fun loadTopRated() {


            val apiService: MyApiService? = MyApiAdapter.getApiService()
            viewModelScope.launch {
                val a = withContext(Dispatchers.IO){


                    val peliculas = repository.getAllPelis()
                    Log.i("room","Respuesta correcta :"+peliculas.toString())
                    if (peliculas.isNotEmpty() ) {
                        listTopRated.postValue(peliculas)

                    }else{
                        val call = apiService?.topRated(Data.API_KEY, Data.LENGUAGE)?.execute()
                        val lista = call?.body()?.results
                            listTopRated.postValue(lista)
                        if (lista != null) {
                            for (movie in lista){
                                repository.insertPeli(movie)
                            }
                        }
                    }
                }
            }

    }
    fun listaFiltrada(newText: String?): ArrayList<Pelicula>{
        var listaFiltrada:ArrayList<Pelicula> = arrayListOf<Pelicula>()
        val listaCopy: List<Pelicula>? = listTopRated.value

        if (listaCopy != null) {
            for (pelicula in listaCopy){
                if (newText?.let { pelicula.title?.lowercase()?.contains(it.lowercase()) } == true){
                    listaFiltrada.add(pelicula)
                }
            }
        }

        return listaFiltrada
    }
    fun addFavorito(peli: Pelicula){
        viewModelScope.launch {
            Log.i("fav","es: "+peli.toString())
            val a = withContext(Dispatchers.IO){
                repository.updatePeli(peli)

            }

        }
    }
}