package com.jhostinlh.topeliculas

import android.app.Application
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.topeliculas.modelo.database.AppDataBase
import com.jhostinlh.topeliculas.modelo.repository.ImplementPelisRepository

class topRatedAplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDataBase.getInstance(this) }
    val apiService by lazy { MyApiAdapter.getApiService() }
    val repository by lazy { apiService!!.let { ImplementPelisRepository(database.pelisDao(), it) } }
}