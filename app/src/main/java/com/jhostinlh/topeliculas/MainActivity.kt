package com.jhostinlh.topeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Modelo.Dao.PelisDao
import com.jhostinlh.topeliculas.Modelo.Database.AppDataBase
import com.jhostinlh.topeliculas.Modelo.Entitys.TopRated
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository
import com.jhostinlh.topeliculas.Modelo.Repository.PelisRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val apiService: MyApiService? = MyApiAdapter.getApiService()

        val call = apiService?.topRated(Data.API_KEY, Data.LENGUAGE)
        if (call != null) {
            call.enqueue(object: Callback<TopRated>{
                override fun onResponse(call: Call<TopRated>, response: Response<TopRated>) {
                    if (!response.isSuccessful){
                        Log.i("responseMainAc","respuesta sin Exito!")
                        return
                    }
                    val respuesta: TopRated? = response.body()
                    Log.i("responseMainAc","Respuesta correcta :"+respuesta.toString())
                    val database = AppDataBase.getInstance(this@MainActivity)
                    val peliDao: PelisDao = database.pelisDao()
                    val repository: PelisRepository = ImplementPelisRepository(peliDao)
                    val peliculas = repository.getAllPelis()

                    if (peliculas.isEmpty() && respuesta != null) {
                        for (peli in respuesta.results)
                        repository.insertPeli(peli)

                    }

                }

                override fun onFailure(call: Call<TopRated>, t: Throwable) {
                    Log.i("responseMainAc","no hay respuesta")
                }

            })
        }

         */

    }
}