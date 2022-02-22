package com.jhostinlh.topeliculas

import android.app.Application
import com.jhostinlh.topeliculas.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Aplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger((Level.ERROR))
            androidContext(this@Aplication)
            modules(listOf(networkModule, dataSourceModule, databaseModule, repositoryModule,
                viewModelModule, favoritesAdapterModule))
        }
    }
}