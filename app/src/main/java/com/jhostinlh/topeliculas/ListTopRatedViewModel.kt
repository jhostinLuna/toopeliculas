package com.jhostinlh.topeliculas

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Modelo.Dao.PelisDao
import com.jhostinlh.topeliculas.Modelo.Database.AppDataBase
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.TopRated
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository
import com.jhostinlh.topeliculas.Modelo.Repository.PelisRepository
import com.jhostinlh.topeliculas.databinding.FragmentListTopRatedBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class ListTopRatedViewModel(application: Application) : AndroidViewModel(application) {
    private val listTopRated: MutableLiveData<List<Pelicula>> by lazy {
        MutableLiveData<List<Pelicula>>().also {
            loadTopRated()
        }
    }

    init {


    }
    fun getTopRated(): LiveData<List<Pelicula>> {
        return listTopRated
    }


    private fun loadTopRated() {


            val apiService: MyApiService? = MyApiAdapter.getApiService()
            viewModelScope.launch {
                val a = withContext(Dispatchers.IO){
                    val context = getApplication<Application>()
                    val database = AppDataBase.getInstance(context)
                    val peliDao: PelisDao = database?.pelisDao()
                    val repository: PelisRepository = ImplementPelisRepository(peliDao)
                    val peliculas = repository.getAllPelis()
                    Log.i("room","Respuesta correcta :"+peliculas.toString())
                    if (peliculas.isNotEmpty() ) {
                        listTopRated.postValue(peliculas)

                    }else{
                        val call = apiService?.topRated(Data.API_KEY, Data.LENGUAGE)?.execute()
                        listTopRated.postValue(call?.body()?.results)
                    }


                }

            }






        /*
        if (call != null) {
            call.enqueue(object: Callback<TopRated> {
                override fun onResponse(call: Call<TopRated>, response: Response<TopRated>) {
                    if (!response.isSuccessful){
                        Log.i("responseMainAc","respuesta sin Exito!")
                        return
                    }
                    val respuesta: TopRated? = response.body()
                    Log.i("responseMainAc","Respuesta correcta :"+respuesta.toString())

                    val database = context?.let { AppDataBase.getInstance(it) }
                    val peliDao: PelisDao = database?.pelisDao() ?: return
                    val repository: PelisRepository = ImplementPelisRepository(peliDao)
                    val peliculas = repository.getAllPelis()
                    mutable = peliculas as MutableList<Pelicula>
                    Log.i("room","Respuesta correcta :"+mutable.toString())
                    if (peliculas.isEmpty() && respuesta != null) {
                        for (peli in respuesta.results)
                            repository.insertPeli(peli)

                    }



                    /*
                        // Set the adapter
                        if (view is RecyclerView) {
                            with(view) {
                                layoutManager = when {
                                    columnCount <= 1 -> LinearLayoutManager(context)
                                    else -> GridLayoutManager(context, columnCount)
                                }
                                adapter = recyclerAdapter
                            }
                        }

                         */

                }

                override fun onFailure(call: Call<TopRated>, t: Throwable) {
                    Log.i("responseMainAc","no hay respuesta")
                }

            })
        }

         */

    }
}