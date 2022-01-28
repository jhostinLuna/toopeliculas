package com.jhostinlh.topeliculas.vistaFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModelFactory
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.ListPeliculasAdapter
import com.jhostinlh.topeliculas.databinding.FragmentListPopulateBinding
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.Aplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListPopulate.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListPopulate : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recycler: RecyclerView
    lateinit var recyclerAdapter: ListPeliculasAdapter
    lateinit var binding: FragmentListPopulateBinding
    val viewModel: ShareRepoViewModel by activityViewModels {
        ShareRepoViewModelFactory((context?.applicationContext as Aplication).repository)
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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListPopulateBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        recycler = binding.recyclerPopulate
        recycler.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)


        viewModel.getListPopular().observe(viewLifecycleOwner,
            object : Observer<List<Movie>> {
                override fun onChanged(t: List<Movie>?) {

                    recyclerAdapter= ListPeliculasAdapter(t!!,this@ListPopulate,viewModel)

                    recycler.adapter = recyclerAdapter

                }

            })


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListPopulate.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListPopulate().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}