package com.jhostinlh.topeliculas.VistaFragments.ListaFavoritos

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.VistaFragments.Adaptadores.FavoritosAdapterRecyclerView
import com.jhostinlh.topeliculas.VistaFragments.ListTopRated.ListTopRatedViewModel
import com.jhostinlh.topeliculas.VistaFragments.Adaptadores.TopRatedAdapter
import com.jhostinlh.topeliculas.VistaFragments.ListTopRated.ListTopRatedViewModelFactory
import com.jhostinlh.topeliculas.databinding.FragmentListTopRatedBinding
import com.jhostinlh.topeliculas.databinding.FragmentListaFavoritosBinding
import com.jhostinlh.topeliculas.topRatedAplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaFavoritos.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaFavoritos : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var topRatedRecycler: RecyclerView
    val viewModel: ListTopRatedViewModel by activityViewModels() {
        ListTopRatedViewModelFactory((context?.applicationContext as topRatedAplication).repository)
    }
    lateinit var recyclerAdapter: FavoritosAdapterRecyclerView
    lateinit var binding: FragmentListaFavoritosBinding
    private var num = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //viewModel = ViewModelProvider(this).get(ListaFavoritosViewModel::class.java)
        Log.i("creado","${num++} veces")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
// Inflate the layout for this fragment
        binding = FragmentListaFavoritosBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)
        topRatedRecycler = binding.topRatedRecyclerLf
        topRatedRecycler.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,false)

        topRatedRecycler.setHasFixedSize(true)

        viewModel.getFavorites().observe(viewLifecycleOwner,object : Observer<List<Pelicula>> {
            override fun onChanged(t: List<Pelicula>?) {
                recyclerAdapter= FavoritosAdapterRecyclerView(t!! as ArrayList<Pelicula>,this@ListaFavoritos,viewModel)

                Log.i("adaptador","itemCount es: "+recyclerAdapter.itemCount)

                topRatedRecycler.adapter = recyclerAdapter


            }

        })

        //busqueda

        binding.editTextTitulopeliculaLf
            .addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    recyclerAdapter?.setFiltro(viewModel.listaFiltrada(s.toString()))
                }

                override fun afterTextChanged(s: Editable) {}
            })






        Log.i("listaPelis","Tiene: "+ (viewModel.getTopRated().value?.get(0)?.backdropPath ?: emptyList<Pelicula>()))



        return binding.root    }

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