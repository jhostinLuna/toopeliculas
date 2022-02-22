package com.jhostinlh.topeliculas.features.vistaFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.extensions.failure
import com.jhostinlh.topeliculas.core.extensions.observe
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.vistaFragments.adaptadores.FavoritosAdapterRecyclerView
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.databinding.FragmentListaFavoritosBinding
import kotlinx.android.synthetic.main.fragment_lista_favoritos.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.scope.Scope

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFavoritesFragment() : BaseFragment(),AndroidScopeComponent {
    override val scope : Scope by fragmentScope()
    val viewModel: ShareRepoViewModel by sharedViewModel()
    private val favoritesAdapter: FavoritosAdapterRecyclerView by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int = R.layout.fragment_lista_favoritos

    fun initializeViews(){
        setHasOptionsMenu(true)
        top_rated_recycler_lf.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,false)

        top_rated_recycler_lf.setHasFixedSize(true)
        top_rated_recycler_lf.adapter = favoritesAdapter
        viewModel.getFavorites().observe(viewLifecycleOwner,object : Observer<List<Pelicula>> {
            override fun onChanged(listaMovies: List<Pelicula>) {
                favoritesAdapter.collection= listaMovies
            }

        })
        top_rated_recycler_lf.adapter = favoritesAdapter
        hideProgress()
    }
    fun initializeListeners(){
        favoritesAdapter.clickListener = {movie->
            val action = ListFavoritesFragmentDirections.actionListaFavoritosToDetallePelicula(movie)
            view?.let { it.findNavController().navigate(action) }
        }
        editText_titulopelicula_lf
            .addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    favoritesAdapter.setFiltro(viewModel.listaFiltrada(s.toString()))
                }

                override fun afterTextChanged(s: Editable) {}
            })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViews()
        initializeListeners()
        /*
        viewModel.getFavorites().observe(viewLifecycleOwner,object : Observer<List<Pelicula>> {
            override fun onChanged(t: List<Pelicula>) {
                top_rated_recycler_lf.adapter = favoritesAdapter
            }

        })

         */



    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ListFavoritesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}