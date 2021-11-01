package com.jhostinlh.topeliculas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhostinlh.topeliculas.modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.repository.ImplementPelisRepository

class PeliculaViewModelFactory(private val repository: ImplementPelisRepository, val pelicula: Pelicula): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetallePeliculaViewModel::class.java)) {
            return DetallePeliculaViewModel(repository,pelicula) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}