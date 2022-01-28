package com.jhostinlh.topeliculas.viewModel


import android.util.Log
import androidx.lifecycle.*
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.entitys.ResultTrailer
import com.jhostinlh.topeliculas.modelo.repository.ImplementPelisRepository
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

data class ShareRepoViewModel(val repository: ImplementPelisRepository) : ViewModel() {
    //val listTopRated: LiveData<List<Pelicula>> = repository.listTopRated!!.asLiveData()
    private val listFavorites: LiveData<List<Pelicula>> = repository.repoFavorite.asLiveData()
    private val listTopRated: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
                loadListTopRated(Data.SERVICE_TOP_RATED)
        }
    }
    private val listPopulate: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
            loadListPopular(Data.SERVICE_POPULATE)
        }
    }
    private val listLatest: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
            loadListLatest(Data.SERVICE_LATEST)
        }
    }

    fun loadListPopular(nameLista:String){
        viewModelScope.launch(Dispatchers.IO) {
            listPopulate.postValue(repository.getListMovies(nameLista)!!.results)
        }
    }
    fun loadListLatest(nameLista:String){
        viewModelScope.launch(Dispatchers.IO) {
            val respuesta =repository.getListMovies(nameLista)!!.results
            for (movie in respuesta) Log.i("respuesta",movie.toString())

            listLatest.postValue(respuesta)

        }
    }
    fun loadListTopRated(nameLista:String){
        viewModelScope.launch(Dispatchers.IO) {
            listTopRated.postValue(repository.getListMovies(nameLista)!!.results)
        }
    }

    fun getTopRated(): LiveData<List<Movie>> {
        return listTopRated
    }
    fun getListPopular(): LiveData<List<Movie>>{
        return listPopulate
    }
    fun getListLatest(): LiveData<List<Movie>>{
        return listLatest
    }
    fun getFavorites(): LiveData<List<Pelicula>> {
        return listFavorites
    }
    suspend fun buscarPelicula(query:String) = repository.buscarPeli(query)?.results

    fun listaFiltrada(newText: String?): ArrayList<Pelicula>{
        val listaFiltrada:ArrayList<Pelicula> = arrayListOf<Pelicula>()
        val listaCopy: List<Pelicula>? = listFavorites.value

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
            try {
                repository.insertPeli(peli)
            }catch (e:Exception){
                Log.d("borrar",e.message+"--"+e.printStackTrace())

            }

        }
    }
    fun remove(peli: Pelicula){
        viewModelScope.launch{
            try {
                repository.deletePeli(peli)
            }catch (e:Exception){
                Log.d("borrar",e.message+"--"+e.printStackTrace())

            }


        }


    }
    suspend fun loadTrailers(idPelicula: Int):List<ResultTrailer> = repository.getListTrailers(idPelicula)!!.resultTrailers


}