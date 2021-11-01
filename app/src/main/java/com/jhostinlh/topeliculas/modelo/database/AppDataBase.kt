package com.jhostinlh.topeliculas.modelo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jhostinlh.topeliculas.modelo.dao.DaoTrailers
import com.jhostinlh.topeliculas.modelo.dao.PelisDao
import com.jhostinlh.topeliculas.modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.Entitys.ResultTrailer

@Database(entities = [Pelicula::class,ResultTrailer::class], version = 5, exportSchema = false)
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
    abstract fun TrailerDao(): DaoTrailers

}