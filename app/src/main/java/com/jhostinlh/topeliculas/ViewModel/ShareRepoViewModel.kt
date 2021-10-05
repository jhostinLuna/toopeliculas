package com.jhostinlh.topeliculas.ViewModel


import android.util.Log
import androidx.lifecycle.*
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

data class ShareRepoViewModel(val repository: ImplementPelisRepository) : ViewModel() {
    //val listTopRated: LiveData<List<Pelicula>> = repository.listTopRated!!.asLiveData()
    private val listFavorites: LiveData<List<Pelicula>> = repository.repoFavorite.asLiveData()
    private val listTopRated: MutableLiveData<List<Pelicula>> by lazy {
        MutableLiveData<List<Pelicula>>().also {
                loadListTopRated(Data.SERVICE_TOP_RATED)
        }
    }
    private val listPopulate: MutableLiveData<List<Pelicula>> by lazy {
        MutableLiveData<List<Pelicula>>().also {
            loadListPopular(Data.SERVICE_POPULATE)
        }
    }
    private val listLatest: MutableLiveData<List<Pelicula>> by lazy {
        MutableLiveData<List<Pelicula>>().also {
            loadListLatest(Data.SERVICE_LATEST)
        }
    }

    fun loadListPopular(nameLista:String){
        viewModelScope.launch(Dispatchers.IO) {
            listPopulate.postValue(repository.actualizarTopRated(nameLista)!!.results)
        }
    }
    fun loadListLatest(nameLista:String){
        viewModelScope.launch(Dispatchers.IO) {
            val respuesta =repository.actualizarTopRated(nameLista)!!.results
            for (movie in respuesta) Log.i("respuesta",movie.toString())

            listLatest.postValue(respuesta)

        }
    }
    fun loadListTopRated(nameLista:String){
        viewModelScope.launch(Dispatchers.IO) {
            listTopRated.postValue(repository.actualizarTopRated(nameLista)!!.results)
        }
    }

    fun getTopRated(): LiveData<List<Pelicula>> {
        return listTopRated
    }
    fun getListPopular(): LiveData<List<Pelicula>>{
        return listPopulate
    }
    fun getListLatest(): LiveData<List<Pelicula>>{
        return listLatest
    }
    fun getFavorites(): LiveData<List<Pelicula>> {
        return listFavorites
    }


    fun listaFiltrada(newText: String?): ArrayList<Pelicula> {
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

}