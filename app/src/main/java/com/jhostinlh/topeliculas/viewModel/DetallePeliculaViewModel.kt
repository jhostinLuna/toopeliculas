package com.jhostinlh.topeliculas.viewModel

import androidx.lifecycle.*
import com.jhostinlh.topeliculas.modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.Entitys.ResultTrailer
import com.jhostinlh.topeliculas.modelo.repository.ImplementPelisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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