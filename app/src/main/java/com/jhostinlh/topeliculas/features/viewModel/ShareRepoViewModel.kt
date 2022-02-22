package com.jhostinlh.topeliculas.features.viewModel


import androidx.lifecycle.*
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.platform.BaseViewModel
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.entitys.ResultTrailer
import com.jhostinlh.topeliculas.features.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.features.modelo.repository.*
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.news.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlin.collections.ArrayList

class ShareRepoViewModel(val repository: MoviesRepository) : BaseViewModel() {
    //val listTopRated: LiveData<List<Pelicula>> = repository.listTopRated!!.asLiveData()
    val listLanguage= listOf("es","en")
    val getMovies = GetMovies(repository)
    val searchMovie = SearchMovie(repository)
    val addFavoriteMovie = AddFavoriteMovie(repository)
    val removeFavoriteMovie = RemoveFavoriteMovie(repository)
    val getTrailer = GetTrailer(repository)
    val getFavorites = GetFavorites(repository)
    lateinit var listFavorites: LiveData<List<Pelicula>> //= repository.repoFavorite.asLiveData()
    val listTopRated: MutableLiveData<List<Movie>> = MutableLiveData()
    val listPopulate: MutableLiveData<List<Movie>> = MutableLiveData()
    val listLatest: MutableLiveData<List<Movie>> = MutableLiveData()
    var resultSearchMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    var resultTrailers: MutableLiveData<List<ResultTrailer>> = MutableLiveData()
    var mensaje:MutableLiveData<String> = MutableLiveData()
    init {
        loadFavorites()
    }
    fun getListMovies(nameList: String) = getMovies.invoke(
        GetMovies.Params(nameList)){
        when(nameList){
            Data.SERVICE_TOP_RATED -> it.fold(::handleFailureEmpty,::handleListTopRated)
            Data.SERVICE_POPULATE -> it.fold(::handleFailureEmpty,::handleListPopular)
            Data.SERVICE_LATEST -> it.fold(::handleFailure,::handleListLatest)
        }
    }
    fun handleFailureEmpty(failure: Failure){}
    fun loadFavorites(){
        getFavorites.invoke(
            GetFavorites.Params()
        ){
            it.fold(::handleFailure,::handleResultFavorites)
        }
    }
    fun handleResultFavorites(lista: Flow<List<Pelicula>>){
        listFavorites = lista.asLiveData()
    }
    private fun handleListTopRated(lista:List<Movie>){
        listTopRated.value = lista
    }
    private fun handleListPopular(lista:List<Movie>){
        listPopulate.value = lista
    }
    private fun handleListLatest(lista:List<Movie>){
        listLatest.value = lista
    }

    fun getTopRated(): LiveData<List<Movie>> {
        return listTopRated
    }
    fun getListPopular(): LiveData<List<Movie>>{
        return listPopulate
    }
    fun getListLatest(): LiveData<List<Movie>>{
        return listLatest
    }
    fun getFavorites(): LiveData<List<Pelicula>> {
        return listFavorites
    }
    fun searchMoview(query:String) = searchMovie.invoke(
        SearchMovie.Params(query)
    ){
        it.fold(::handleFailure,::handleSearchQuery)
    }
    fun handleSearchQuery(listaMovies:List<Movie>){
        resultSearchMovie.value = listaMovies
    }
    fun listaFiltrada(newText: String?): ArrayList<Pelicula>{
        val listaFiltrada:ArrayList<Pelicula> = arrayListOf<Pelicula>()
        val listaCopy: List<Pelicula>? = listFavorites.value

        if (listaCopy != null) {
            for (pelicula in listaCopy){
                if (newText?.let { pelicula.title?.lowercase()?.contains(it.lowercase()) } == true){
                    listaFiltrada.add(pelicula)
                }
            }
        }

        return listaFiltrada
    }



    fun addFavorito(movie: Movie){
        addFavoriteMovie.invoke(
            AddFavoriteMovie.Params(movie)
        ){
            it.fold(::handleFailure,::handleAddFavorite)
        }
    }
    fun handleAddFavorite(any: Any){
        mensaje.value = "Se ha añadido Correctamente!!"
    }
    fun remove(movie: Movie){
        removeFavoriteMovie.invoke(
            RemoveFavoriteMovie.Params(movie)
        ){
            it.fold(::handleFailure,::handleRemoveFavoriteMovie)
        }
    }
    fun handleRemoveFavoriteMovie(any: Any){
        mensaje.value = "Eliminada!!"
    }
    fun loadTrailers(idMovie: Int,language:String){
        getTrailer.invoke(
            GetTrailer.Params(idMovie,language)
        ){
            it.fold(::handleFailure,::handleListTrailers)
        }

    }
    fun handleListTrailers(trailer: Trailer){

        resultTrailers.value = trailer.resultTrailers
    }

}