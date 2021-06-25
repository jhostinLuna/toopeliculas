package com.jhostinlh.topeliculas.Modelo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jhostinlh.topeliculas.Modelo.Dao.PelisDao
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula

@Database(entities = [Pelicula::class], version = 1)
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