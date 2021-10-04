package com.jhostinlh.topeliculas.VistaFragments.ListaFavoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ListaFavoritosViewModel(application: Application) : AndroidViewModel(application) {
    /*
    private val listTopRated: MutableLiveData<ArrayList<Pelicula>> by lazy {
        MutableLiveData<ArrayList<Pelicula>>().also {
            loadTopRated()
        }
    }


    fun getTopRated(): LiveData<ArrayList<Pelicula>> {
        return listTopRated
    }
    private val context = getApplication<Application>().applicationContext
    private val database = AppDataBase.getInstance(context)
    private val peliDao: PelisDao = database?.pelisDao()
    private val repository: PelisRepository = ImplementPelisRepository(peliDao)
    /*
    private fun loadTopRated() {


        viewModelScope.launch {
            val a = withContext(Dispatchers.IO){


                val peliculas = repository.getFavoritos() as ArrayList<Pelicula>
                Log.i("room","Respuesta correcta :"+peliculas.toString())
                listTopRated.postValue(peliculas)

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
        val ok = listTopRated.value?.remove(peli)
        if (ok == true){
            Log.i("borrado","la lista contiene la pelicula: ->"+ peli.toString())
        }else{
            Log.i("borrado","No la contiene")

        }

    }
    fun verifyContain(peli: Pelicula){
        val ok = listTopRated.value?.remove(peli)
        if (ok == true){
            Log.i("borrado","la lista contiene la pelicula: ->"+ peli.toString())
        }else{
            Log.i("borrado","No la contiene")

        }
    }

     */
}