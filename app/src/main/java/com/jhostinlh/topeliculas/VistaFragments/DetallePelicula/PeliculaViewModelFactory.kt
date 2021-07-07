package com.jhostinlh.topeliculas.VistaFragments.DetallePelicula

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula

class PeliculaViewModelFactory(val app: Application,val pelicula: Pelicula): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetallePeliculaViewModel::class.java)) {
            return DetallePeliculaViewModel(app,pelicula) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}