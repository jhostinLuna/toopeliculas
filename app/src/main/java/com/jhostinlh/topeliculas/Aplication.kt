package com.jhostinlh.topeliculas

import android.app.Application
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.topeliculas.modelo.database.AppDataBase
import com.jhostinlh.topeliculas.vistaFragments.RemoteRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Aplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
}