package com.jhostinlh.topeliculas.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class GroupListViewModel (application: Application) : AndroidViewModel(application) {
    /*
    private val listTopRated: MutableLiveData<List<Pelicula>> by lazy {
        MutableLiveData<List<Pelicula>>().also {
            loadTopRated()
        }
    }

     */

    /*
    fun getTopRated(): LiveData<List<Pelicula>> {
        return listTopRated
    }
    private val context = getApplication<Application>().applicationContext
    private val database = AppDataBase.getInstance(context)
    private val peliDao: PelisDao = database?.pelisDao()
    private val repository: PelisRepository = ImplementPelisRepository(peliDao)
    private val fireDatabase: FirebaseDatabase = Firebase.database("https://infopelis-33b1a-default-rtdb.europe-west1.firebasedatabase.app/")
    val reference: DatabaseReference = fireDatabase.getReference("ListaTopRated")
    val apiService: MyApiService? = MyApiAdapter.getApiService()

    private fun firebaseTopRated(listaPeli: ArrayList<Pelicula>){


        for (peli in listaPeli){
            reference.push().setValue(peli)
        }

    }
    private suspend fun leerFireDatabase(): ArrayList<Pelicula>{
        val listaPeli: ArrayList<Pelicula> = arrayListOf()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()){

                    return
                }
                for (peli in snapshot.children){
                    listaPeli.add(peli.getValue(Pelicula::class.java) as Pelicula)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("firebase","cancelado")
            }

        })



        return listaPeli
    }
    private suspend fun actualizarTopRated(){
        val call = apiService?.topRated(Data.API_KEY, Data.LENGUAGE)?.execute()
        val lista = call?.body()?.results
        if (!lista?.equals(listTopRated.value)!!){
            listTopRated.postValue(lista)

            firebaseTopRated(lista)
            for (movie in lista){
                repository.insertPeli(movie)
            }

        }
    }

     */
    /*
    private fun loadTopRated() {
        var peliculas:List<Pelicula> = listOf()

        viewModelScope.launch {
            val a = withContext(Dispatchers.IO){


                peliculas = repository.getAllPelis()
                Log.i("room","Respuesta correcta :"+peliculas.toString())
                if (peliculas.isNotEmpty() ) {
                    listTopRated.postValue(peliculas)

                }else{


                    val call = apiService?.topRated(Data.API_KEY, Data.LENGUAGE)?.execute()
                    val lista = call?.body()?.results
                    listTopRated.postValue(lista)
                    if (lista != null) {
                        firebaseTopRated(lista)
                        for (movie in lista) {
                            repository.insertPeli(movie)
                        }
                    }

                }
            }
        }

    }

     */
/*
    fun listaFiltrada(newText: String?): ArrayList<Pelicula>{
        var listaFiltrada:ArrayList<Pelicula> = arrayListOf<Pelicula>()
        val listaCopy: List<Pelicula>? = listTopRated.value

        if (listaCopy != null) {
            for (pelicula in listaCopy){
                if (newText?.let { pelicula.title?.lowercase()?.contains(it.lowercase()) } == true){
                    listaFiltrada.add(pelicula)
                }
            }
        }

        return listaFiltrada
    }
    fun addFavorito(peli: Pelicula){
        viewModelScope.launch {
            Log.i("fav","es: "+peli.toString())
            val a = withContext(Dispatchers.IO){
                repository.updatePeli(peli)

            }

        }
    }

 */
}