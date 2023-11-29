package com.jhostinlh.topeliculas.vistaFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import com.jhostinlh.topeliculas.databinding.FragmentBuscarPeliBinding
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.ListPeliculasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val BUNDLE = "query"

/**
 * A simple [Fragment] subclass.
 * Use the [BuscarPeli.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class BuscarPeli : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var query: String? = null
    private lateinit var recyclerAdapter: ListPeliculasAdapter
    private lateinit var binding: FragmentBuscarPeliBinding
    val viewModel: ShareRepoViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(BUNDLE)){
                query = it.getString(BUNDLE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBuscarPeliBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (query == null){
            binding.textViewMensajeFragmentBuscarPeli.visibility = View.VISIBLE
            return
        }else {
            query?.let {
                viewModel.searchMovie(it)
            }
            binding.recyclerViewBuscarPeli.layoutManager = LinearLayoutManager(
                this.context,
                LinearLayoutManager.VERTICAL,false)
            viewModel.getSearchResult().observe(viewLifecycleOwner
            ) { searchResult ->
                recyclerAdapter =
                    ListPeliculasAdapter(searchResult, this@BuscarPeli, viewModel)
                binding.recyclerViewBuscarPeli.adapter = recyclerAdapter
            }
        }


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
                    query = getString(BUNDLE)
                }
            }
    }

}