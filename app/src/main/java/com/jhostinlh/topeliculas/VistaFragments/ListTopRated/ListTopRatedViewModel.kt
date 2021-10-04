package com.jhostinlh.topeliculas.VistaFragments.ListTopRated


import android.util.Log
import androidx.lifecycle.*
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ListTopRatedViewModel(val repository: ImplementPelisRepository) : ViewModel() {
    //val listTopRated: LiveData<List<Pelicula>> = repository.listTopRated!!.asLiveData()
    val listFavorites: LiveData<List<Pelicula>> = repository.repoFavorite.asLiveData()
    private val listTopRated: MutableLiveData<List<Pelicula>> by lazy {
        MutableLiveData<List<Pelicula>>().also {
                loadLista()
        }
    }
    fun loadLista(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizarTopRated()
            listTopRated.postValue(repository.listTopRated)
        }
    }
    init {
        for (movie in repository.listTopRated) Log.i("pruebita",movie.toString())

    }

    fun getTopRated(): LiveData<List<Pelicula>> {
        return listTopRated
    }
    fun getFavorites(): LiveData<List<Pelicula>> {
        return listFavorites
    }



    /*
    private suspend fun actualizarTopRated(){
        val call = apiService?.topRated(Data.API_KEY, Data.LENGUAGE)?.execute()
        val lista = call?.body()?.results
        if (!lista?.equals(listTopRated.value)!!){
            listTopRated.postValue(lista)

            for (movie in lista){
                repository.insertPeli(movie)
            }

        }
    }


    */

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
    fun remove(peli: Pelicula){
        viewModelScope.launch{
            val ok = repository.deletePeli(peli)

        }


    }

    fun actualizaTopRated(){
        viewModelScope.launch {
            val ok = repository.actualizarTopRated()
        }
    }

}