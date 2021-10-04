package com.jhostinlh.topeliculas.VistaFragments.ListTopRated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository

class ListTopRatedViewModelFactory(private val repository: ImplementPelisRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListTopRatedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListTopRatedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}