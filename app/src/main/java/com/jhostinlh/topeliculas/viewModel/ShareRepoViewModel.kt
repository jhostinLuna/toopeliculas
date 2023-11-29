package com.jhostinlh.topeliculas.viewModel


import android.util.Log
import androidx.lifecycle.*
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.core.platform.BaseViewModel
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.entitys.ResultTrailer
import com.jhostinlh.topeliculas.modelo.entitys.Trailer
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.ObjMovies
import com.jhostinlh.topeliculas.vistaFragments.GetListMoviesUseCase
import com.jhostinlh.topeliculas.vistaFragments.GetSearchMovieUseCase
import com.jhostinlh.topeliculas.vistaFragments.GetTrailersUseCase
import com.jhostinlh.topeliculas.vistaFragments.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList
@HiltViewModel
data class ShareRepoViewModel @Inject constructor(
    val repository: RemoteRepository,
    private val getListMoviesUseCase: GetListMoviesUseCase,
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
    private val getTrailersUseCase: GetTrailersUseCase
) : BaseViewModel() {
    //val listTopRated: LiveData<List<Pelicula>> = repository.listTopRated!!.asLiveData()
    private val listFavorites: LiveData<List<Pelicula>> = repository.getLocalFavorites().asLiveData()
    private val listTopRated: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
        }
    }
    private val listPopulate: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {

        }
    }
    private val listLatest: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {

        }
    }

    private fun handleListPopular(objMovies: ObjMovies) {
        this.listPopulate.value = objMovies.results
    }
    private fun handleListLatest(objMovies: ObjMovies) {
        this.listLatest.value = objMovies.results
    }
    fun loadListPopular() {
        getListMoviesUseCase.invoke(GetListMoviesUseCase.Params(Data.SERVICE_POPULATE)){
            it.fold(::handleFailure,::handleListPopular)
        }
    }
    fun loadListTopRated(){
        getListMoviesUseCase.invoke(GetListMoviesUseCase.Params(Data.SERVICE_TOP_RATED)){
            it.fold(::handleFailure,::handleListTopRated)
        }
    }
    fun loadListLatest() {
        getListMoviesUseCase.invoke(GetListMoviesUseCase.Params(Data.SERVICE_LATEST)){
            it.fold(::handleFailure,::handleListLatest)
        }
    }
    private fun handleListTopRated(objMovies: ObjMovies) {
        this.listTopRated.value = objMovies.results
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

    private val listResultQuery:MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    fun getSearchResult(): LiveData<List<Movie>> {
        return listResultQuery
    }
    fun searchMovie(query:String) {
        resetListSearchResult()
        getSearchMovieUseCase.invoke(GetSearchMovieUseCase.Params(query = query)){
            it.fold(::handleFailure,::handleSearchResult)
        }
    }
    private fun resetListSearchResult(){
        listResultQuery.value = emptyList()
    }
    private fun handleSearchResult(objMovie: ObjMovies){
        listResultQuery.value = objMovie.results
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



    fun addFavorito(peli: Pelicula){
        viewModelScope.launch {
            try {
                repository.insertPeli(peli)
            }catch (e:Exception){
                Log.d("borrar",e.message+"--"+e.printStackTrace())

            }

        }
    }
    fun remove(peli: Pelicula){
        viewModelScope.launch{
            try {
                repository.deletePeli(peli)
            }catch (e:Exception){
                Log.d("borrar",e.message+"--"+e.printStackTrace())

            }


        }


    }
    private val listTrailer: MutableLiveData<List<ResultTrailer>> = MutableLiveData()
    fun loadTrailers(idPelicula: Int) {
        getTrailersUseCase.invoke(GetTrailersUseCase.Params(idPelicula)){
            it.fold(::handleFailure,::handleTrailersResult)
        }
    }
    private fun handleTrailersResult(trailer: Trailer){
        listTrailer.value = trailer.resultTrailers
    }
    fun getListTrailers():LiveData<List<ResultTrailer>> {
        return listTrailer
    }
}