package com.jhostinlh.topeliculas.VistaFragments.ListTopRated

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.VistaFragments.Adaptadores.TopRatedAdapter
import com.jhostinlh.topeliculas.databinding.FragmentListTopRatedBinding
import com.jhostinlh.topeliculas.topRatedAplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListTopRated.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListTopRated : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var topRatedRecycler: RecyclerView
    lateinit var recyclerAdapter: TopRatedAdapter
    lateinit var binding: FragmentListTopRatedBinding
    val viewModel: ListTopRatedViewModel by activityViewModels() {
        ListTopRatedViewModelFactory((context?.applicationContext as topRatedAplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //viewModel = ViewModelProvider(this).get(ListTopRatedViewModel::class.java)
        recyclerAdapter = TopRatedAdapter(emptyList(), this,viewModel)
        viewModel.actualizaTopRated()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentListTopRatedBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)
        topRatedRecycler = binding.topRatedRecyclerLf
        topRatedRecycler.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)

        /*
        viewModel.getTopRated().observe(viewLifecycleOwner, Observer<List<Pelicula>>{ listTopRated ->
             recyclerAdapter= TopRatedAdapter(listTopRated)
            topRatedRecycler.adapter = recyclerAdapter

        })

         */

        viewModel.getTopRated().observe(viewLifecycleOwner,object : Observer<List<Pelicula>>{
            override fun onChanged(t: List<Pelicula>?) {
                for (movie in t!!) Log.i("probando",movie.toString())

                recyclerAdapter= TopRatedAdapter(t!!,this@ListTopRated,viewModel)

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


        return binding.root
    }

/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }


 */

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListTopRated.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListTopRated().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}