package com.jhostinlh.topeliculas.core.di

import android.content.Context
import androidx.room.Room
import com.jhostinlh.topeliculas.modelo.retrofit.MyApiService
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.modelo.dao.PelisDao
import com.jhostinlh.topeliculas.modelo.database.AppDataBase
import com.jhostinlh.topeliculas.vistaFragments.RemoteRepository
import com.jhostinlh.topeliculas.vistaFragments.RemoteRepositoryImplement
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {
    @Provides
    fun provideMyApiService(): MyApiService {
        return Retrofit.Builder()
            .baseUrl(Data.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApiService::class.java)
    }
    @Provides
    fun provideAppdataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java,"checklist.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun instanceAppDataBase(appDataBase: AppDataBase): PelisDao = appDataBase.pelisDao()
}
@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteRepoModule {
    @Binds
    abstract fun getRemoteRepository(remoteRepositoryImplement: RemoteRepositoryImplement): RemoteRepository
}