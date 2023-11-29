package com.jhostinlh.topeliculas.vistaFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.FavoritosAdapterRecyclerView
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.databinding.FragmentListaFavoritosBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaFavoritos.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListaFavoritos : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var topRatedRecycler: RecyclerView
    val viewModel: ShareRepoViewModel by activityViewModels()
    lateinit var favoritosAdapter: FavoritosAdapterRecyclerView
    private lateinit var binding: FragmentListaFavoritosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //viewModel = ViewModelProvider(this).get(ListaFavoritosViewModel::class.java)
        favoritosAdapter= FavoritosAdapterRecyclerView(emptyList(),context,viewModel)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
// Inflate the layout for this fragment
        binding = FragmentListaFavoritosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        topRatedRecycler = binding.topRatedRecyclerLf
        topRatedRecycler.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,false)

        topRatedRecycler.setHasFixedSize(true)

        viewModel.getFavorites().observe(viewLifecycleOwner,object : Observer<List<Pelicula>> {
            override fun onChanged(t: List<Pelicula>) {
                favoritosAdapter= FavoritosAdapterRecyclerView(t,context,viewModel)

                Log.i("adaptador","itemCount es: "+favoritosAdapter.itemCount)

                topRatedRecycler.adapter = favoritosAdapter


            }

        })

        binding.editTextTitulopeliculaLf
            .addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    favoritosAdapter.setFiltro(viewModel.listaFiltrada(s.toString()))
                }

                override fun afterTextChanged(s: Editable) {}
            })

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaFavoritos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaFavoritos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}