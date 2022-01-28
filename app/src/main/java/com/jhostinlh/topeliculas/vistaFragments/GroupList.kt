package com.jhostinlh.topeliculas.vistaFragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModelFactory
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.GroupListRecyclerAdapter
import com.jhostinlh.topeliculas.databinding.FragmentGroupListBinding
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.Aplication
import com.jhostinlh.topeliculas.R
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
    val viewModel: ShareRepoViewModel by activityViewModels {
        ShareRepoViewModelFactory((context?.applicationContext as Aplication).repository)
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
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =   FragmentGroupListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerTopRated = binding.recyclerToprated
        recyclerTopRated.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recyclerTopRated.setHasFixedSize(true)
        viewModel.getTopRated().observe(viewLifecycleOwner,
            Observer<List<Movie>> { t ->
                recyclerAdapterTopRated= GroupListRecyclerAdapter(t!!)

                recyclerTopRated.adapter = recyclerAdapterTopRated
                recyclerAdapterTopRated.setOnClickListener {view ->
                    val pelicula = t[recyclerTopRated.getChildAdapterPosition(view)]
                    val action = GroupListDirections.actionGroupListToDetallePelicula(pelicula)
                    this@GroupList.findNavController().navigate(action)

                }
            })



        recyclerPopular = binding.recyclerPopular
        recyclerPopular.layoutManager= LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recyclerPopular.setHasFixedSize(true)

        viewModel.getListPopular().observe(viewLifecycleOwner,
            object : Observer<List<Movie>> {
                override fun onChanged(t: List<Movie>?) {

                    recyclerAdapterPopular= GroupListRecyclerAdapter(t!!)

                    recyclerPopular.adapter = recyclerAdapterPopular
                    recyclerAdapterPopular.setOnClickListener {view ->
                        val pelicula = t[recyclerPopular.getChildAdapterPosition(view!!)]
                        val action = GroupListDirections.actionGroupListToDetallePelicula(pelicula)
                        this@GroupList.findNavController().navigate(action)

                    }

                }

            })

        recyclerLatest = binding.recyclerLatest
        recyclerLatest.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recyclerLatest.setHasFixedSize(true)

        viewModel.getListLatest().observe(viewLifecycleOwner,
            object : Observer<List<Movie>> {
                override fun onChanged(t: List<Movie>?) {

                    recyclerAdapterLatest= GroupListRecyclerAdapter(t!!)

                    recyclerLatest.adapter = recyclerAdapterLatest
                    recyclerAdapterLatest.setOnClickListener { view ->
                        val pelicula = t[recyclerLatest.getChildAdapterPosition(view!!)]
                        val action = GroupListDirections.actionGroupListToDetallePelicula(pelicula)
                        this@GroupList.findNavController().navigate(action)
                    }
                }

            })
        binding.textViewMoreLatest.setOnClickListener { view.findNavController().navigate(R.id.listLatest) }
        binding.textViewMorePopulate.setOnClickListener { view.findNavController().navigate(R.id.listPopulate) }
        binding.textViewMoreToprated.setOnClickListener { view.findNavController().navigate(R.id.listTopRated) }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val itemFavoritos = menu.findItem(R.id.itemFavorito)
        itemFavoritos.isVisible = false
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