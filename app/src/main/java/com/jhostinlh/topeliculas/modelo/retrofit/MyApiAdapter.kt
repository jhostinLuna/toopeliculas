package com.jhostinlh.tiempokotlin.Retrofit

import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.modelo.retrofit.MyApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApiAdapter {
    companion object {
        private var API_SERVICE: MyApiService? = null
        fun getApiService(): MyApiService? {

            if (API_SERVICE == null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(Data.URL_BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                API_SERVICE = retrofit.create(MyApiService::class.java)

            }
            return API_SERVICE
        }
    }
}