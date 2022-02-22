package com.jhostinlh.topeliculas.features.vistaFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhostinlh.topeliculas.NavGraphDirections
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.extensions.failure
import com.jhostinlh.topeliculas.core.extensions.observe
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.features.vistaFragments.adaptadores.ListMovieAdapter
import kotlinx.android.synthetic.main.fragment_buscar_peli.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.coroutines.CoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BUNDLE = "query"

/**
 * A simple [Fragment] subclass.
 * Use the [BuscarPeli.newInstance] factory method to
 * create an instance of this fragment.
 */
class BuscarPeli : BaseFragment(){
    // TODO: Rename and change types of parameters
    //Heredar CoroutineScope
    /*
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private lateinit var job: Job

     */
    private var query: String? = null
    val listMovieAdapter: ListMovieAdapter = ListMovieAdapter()
    val viewModel: ShareRepoViewModel by sharedViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(BUNDLE)){
                query = it.getString(BUNDLE)
            }
        }
        //job = Job()
        with(viewModel){
            observe(resultSearchMovie,::renderListSearchMovie)
            failure(failure,::handleFailure)
        }
        loadResultSearchMovies()

    }
    fun renderListSearchMovie(listMovie: List<Movie>?){
        listMovieAdapter.collection = filterMovie(listMovie.orEmpty())
        hideProgress()
    }
    private fun handleFailure(failure: Failure?){
        viewModel.failure = MutableLiveData<Failure>()
        when(failure) {
            is Failure.NetworkConnection -> renderFailure(0,context?.resources?.getString(R.string.error_conection_internet))
            is Failure.CustomError -> renderFailure(failure.errorCode, context?.resources?.getString(R.string.error_server))
            else -> renderFailure(0, "¡Error desconocido intentalo mas tarde!!")
        }
    }
    private fun renderFailure(errorCode: Int, errorMessage: String?) {
        hideProgress()
        showError(errorCode, errorMessage, object : DialogCallback {
            override fun onAccept() {
                loadResultSearchMovies()
            }

            override fun onDecline() {
                onBackPressed()
            }
        })
    }

    private fun loadResultSearchMovies() {
        showProgress()
        query?.let { viewModel.searchMoview(it) }
    }

    override fun layoutId(): Int = R.layout.fragment_buscar_peli
    private fun initializeView(){
        if (query == null){
            textViewMensajeFragmentBuscarPeli.visibility = View.VISIBLE
            hideProgress()
            return
        }

        recyclerViewBuscarPeli.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,false)
        recyclerViewBuscarPeli.adapter = listMovieAdapter

    }
    private fun initializeListener(){
        listMovieAdapter.clickListener = { movie->
            val action = NavGraphDirections.actionGlobalDetallePelicula(movie)
            view?.let { it.findNavController().navigate(action) }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        initializeListener()
    }
    fun filterMovie(lista: List<Movie>): List<Movie>{
        val listaFiltrada = arrayListOf<Movie>()
        lista.forEach {
            if (!it.overview.isNullOrEmpty()){
                listaFiltrada.add(it)
            }
        }
        return listaFiltrada.toList()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetallePelicula.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BuscarPeli().apply {
                arguments = Bundle().apply {
                }
            }
    }

}