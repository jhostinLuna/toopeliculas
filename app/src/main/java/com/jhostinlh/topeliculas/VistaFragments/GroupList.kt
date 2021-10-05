package com.jhostinlh.topeliculas.VistaFragments


import android.os.Bundle
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
import com.jhostinlh.topeliculas.VistaFragments.Adaptadores.GroupListRecyclerAdapter
import com.jhostinlh.topeliculas.databinding.FragmentGroupListBinding
import com.jhostinlh.topeliculas.topRatedAplication
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupList.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModel: ShareRepoViewModel by activityViewModels() {
        ShareRepoViewModelFactory((context?.applicationContext as topRatedAplication).repository)
    }
    lateinit var recyclerTopRated: RecyclerView
    lateinit var recyclerPopular: RecyclerView
    lateinit var recyclerLatest: RecyclerView

    lateinit var recyclerAdapterTopRated: GroupListRecyclerAdapter
    lateinit var recyclerAdapterPopular: GroupListRecyclerAdapter
    lateinit var recyclerAdapterLatest: GroupListRecyclerAdapter

    lateinit var binding: FragmentGroupListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =   FragmentGroupListBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)

        recyclerTopRated = binding.recyclerToprated
        recyclerTopRated.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recyclerTopRated.setHasFixedSize(true)
        viewModel.getTopRated().observe(viewLifecycleOwner,
            object : Observer<List<Pelicula>> {
                override fun onChanged(t: List<Pelicula>?) {

                    recyclerAdapterTopRated= GroupListRecyclerAdapter(t!!)

                    recyclerTopRated.adapter = recyclerAdapterTopRated

                }

            })



        recyclerPopular = binding.recyclerPopular
        recyclerPopular.layoutManager= LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recyclerPopular.setHasFixedSize(true)

        viewModel.getListPopular().observe(viewLifecycleOwner,
            object : Observer<List<Pelicula>> {
                override fun onChanged(t: List<Pelicula>?) {

                    recyclerAdapterPopular= GroupListRecyclerAdapter(t!!)

                    recyclerPopular.adapter = recyclerAdapterPopular

                }

            })

        recyclerLatest = binding.recyclerLatest
        recyclerLatest.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recyclerLatest.setHasFixedSize(true)

        viewModel.getListLatest().observe(viewLifecycleOwner,
            object : Observer<List<Pelicula>> {
                override fun onChanged(t: List<Pelicula>?) {

                    recyclerAdapterLatest= GroupListRecyclerAdapter(t!!)

                    recyclerLatest.adapter = recyclerAdapterLatest

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
         * @return A new instance of fragment GroupList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}