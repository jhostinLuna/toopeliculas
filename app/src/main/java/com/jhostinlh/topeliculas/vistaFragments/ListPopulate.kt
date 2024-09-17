package com.jhostinlh.topeliculas.vistaFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.ListPeliculasAdapter
import com.jhostinlh.topeliculas.databinding.FragmentListPopulateBinding
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListPopulate : BaseFragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var recyclerAdapter: ListPeliculasAdapter
    private lateinit var binding: FragmentListPopulateBinding
    val viewModel: ShareRepoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        viewModel.getListPopular().observe(viewLifecycleOwner
        ) { value ->
            recyclerAdapter = ListPeliculasAdapter(value, this@ListPopulate, viewModel)

            recycler.adapter = recyclerAdapter
        }


    }
}