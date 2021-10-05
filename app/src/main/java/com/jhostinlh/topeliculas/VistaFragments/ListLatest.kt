package com.jhostinlh.topeliculas.VistaFragments

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
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.ViewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.ViewModel.ShareRepoViewModelFactory
import com.jhostinlh.topeliculas.VistaFragments.Adaptadores.FavoritosAdapterRecyclerView
import com.jhostinlh.topeliculas.VistaFragments.Adaptadores.ListPeliculasAdapter
import com.jhostinlh.topeliculas.databinding.FragmentListLatestBinding
import com.jhostinlh.topeliculas.databinding.FragmentListTopRatedBinding
import com.jhostinlh.topeliculas.topRatedAplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListLatest.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListLatest : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recycler: RecyclerView
    lateinit var recyclerAdapter: ListPeliculasAdapter
    lateinit var binding: FragmentListLatestBinding
    val viewModel: ShareRepoViewModel by activityViewModels() {
        ShareRepoViewModelFactory((context?.applicationContext as topRatedAplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        recyclerAdapter = ListPeliculasAdapter(emptyList(), this,viewModel)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListLatestBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)
        recycler = binding.recyclerLatest
        recycler.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)


        viewModel.getListLatest().observe(viewLifecycleOwner,
            object : Observer<List<Pelicula>>{
                override fun onChanged(t: List<Pelicula>?) {

                    recyclerAdapter= ListPeliculasAdapter(t!!,this@ListLatest,viewModel)

                    recycler.adapter = recyclerAdapter

                }

            })


        return binding.root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListLatest.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListLatest().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}