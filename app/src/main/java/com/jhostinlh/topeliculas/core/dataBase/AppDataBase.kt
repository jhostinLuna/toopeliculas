package com.jhostinlh.topeliculas.core.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jhostinlh.topeliculas.core.dao.PelisDao
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula

@Database(entities = [Pelicula::class], version = 8, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    companion object{
        var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, AppDataBase::class.java,"checklist.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDataBase
        }
    }
    abstract fun pelisDao() : PelisDao

}