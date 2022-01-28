package com.jhostinlh.topeliculas.vistaFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.Aplication
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.databinding.FragmentBuscarPeliBinding
import com.jhostinlh.topeliculas.databinding.FragmentListLatestBinding
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModelFactory
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.ListPeliculasAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BUNDLE = "query"

/**
 * A simple [Fragment] subclass.
 * Use the [BuscarPeli.newInstance] factory method to
 * create an instance of this fragment.
 */
class BuscarPeli : Fragment(),CoroutineScope {
    // TODO: Rename and change types of parameters
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private lateinit var job: Job
    private var query: String? = null
    lateinit var recyclerAdapter: ListPeliculasAdapter
    lateinit var binding: FragmentBuscarPeliBinding
    var resultsMovie: List<Movie>? =null
    val viewModel: ShareRepoViewModel by activityViewModels {
        ShareRepoViewModelFactory((context?.applicationContext as Aplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(BUNDLE)){
                query = it.getString(BUNDLE)
            }
        }
        job = Job()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBuscarPeliBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (query == null){
            binding.textViewMensajeFragmentBuscarPeli.visibility = View.VISIBLE
            return
        }
        binding.recyclerViewBuscarPeli.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,false)
        launch {
            resultsMovie = query?.let { viewModel.buscarPelicula(it) }
            recyclerAdapter = resultsMovie?.let { ListPeliculasAdapter(filtrarMovie(it),this@BuscarPeli,viewModel) }!!
            binding.recyclerViewBuscarPeli.adapter = recyclerAdapter
        }
    }
    fun filtrarMovie(lista: List<Movie>): List<Movie>{
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