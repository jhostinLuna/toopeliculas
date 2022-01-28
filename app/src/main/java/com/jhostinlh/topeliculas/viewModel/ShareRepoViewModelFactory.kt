package com.jhostinlh.topeliculas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhostinlh.topeliculas.modelo.repository.ImplementPelisRepository

class ShareRepoViewModelFactory(private val repository: ImplementPelisRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShareRepoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShareRepoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}