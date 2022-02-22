package com.jhostinlh.topeliculas.features.vistaFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.features.vistaFragments.adaptadores.ListMovieAdapter
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_list_movies.*
import kotlinx.android.synthetic.main.partial_list_movies.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BUNDLE = "nameList"

/**
 * A simple [Fragment] subclass.
 * Use the [ListMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListMoviesFragment : BaseFragment() {

    val listMoviesAdapter: ListMovieAdapter = ListMovieAdapter()
    val viewModel: ShareRepoViewModel by sharedViewModel()
    var listMovies: List<Movie> = emptyList()
    var nameList: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(BUNDLE)){
                nameList = it.getString(BUNDLE).orEmpty()
            }
        }
        listMovies = when(nameList){
            Data.SERVICE_POPULATE -> viewModel.listPopulate.value.orEmpty()
            Data.SERVICE_TOP_RATED -> viewModel.listTopRated.value.orEmpty()
            Data.SERVICE_LATEST -> viewModel.listLatest.value.orEmpty()
            else -> emptyList()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_list_movies
    private fun initializeViews(){
        setHasOptionsMenu(true)
        recycler_latest.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)
        listMoviesAdapter.collection = listMovies
        recycler_latest.adapter = listMoviesAdapter
        hideProgress()
    }
    private fun initializeListeners(){
        listMoviesAdapter.clickListener = {movie->
            val action = ListMoviesFragmentDirections.actionListLatestToDetallePelicula(movie)
            view?.let { it.findNavController().navigate(action) }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeListeners()
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
        fun newInstance(param1: String) =
            ListMoviesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}