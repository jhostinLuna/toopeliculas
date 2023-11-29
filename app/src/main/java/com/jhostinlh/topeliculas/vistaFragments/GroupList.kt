package com.jhostinlh.topeliculas.vistaFragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.GroupListRecyclerAdapter
import com.jhostinlh.topeliculas.databinding.FragmentGroupListBinding
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.extensions.observe
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
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
@AndroidEntryPoint
class GroupList : BaseFragment() {
    val viewModel: ShareRepoViewModel by activityViewModels()

    private val recyclerAdapterTopRated = GroupListRecyclerAdapter()
    private val recyclerAdapterPopular = GroupListRecyclerAdapter()
    private val recyclerAdapterLatest = GroupListRecyclerAdapter()

    private lateinit var binding: FragmentGroupListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        loadListMovies()
        with(viewModel){
            observe(getTopRated(),::renderListTopRated)
            observe(getListPopular(), ::renderListPopular)
            observe(getListLatest(), :: renderListLatest)
            observe(failure, :: handleFailure)
        }
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
        initializeViews()
        initListeners()

    }
    private fun initializeViews() {
        //Popular
        binding.recyclerPopular.adapter = recyclerAdapterPopular
        binding.recyclerPopular.layoutManager= LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerPopular.setHasFixedSize(true)
        //TopRated
        binding.recyclerTopRated.adapter = recyclerAdapterTopRated
        binding.recyclerTopRated.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerTopRated.setHasFixedSize(true)
        /////Latest
        binding.recyclerLatest.adapter = recyclerAdapterLatest
        binding.recyclerLatest.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerLatest.setHasFixedSize(true)

    }
    private fun initListeners() {
        recyclerAdapterPopular.setOnClickListener { view ->
            val movie = recyclerAdapterPopular.collection[binding.recyclerPopular.getChildAdapterPosition(view)]
            val action = GroupListDirections.actionGroupListToDetallePelicula(movie)
            this@GroupList.findNavController().navigate(action)

        }
        recyclerAdapterTopRated.setOnClickListener {view->
            val movie = recyclerAdapterTopRated.collection[binding.recyclerTopRated.getChildAdapterPosition(view)]
            val action = GroupListDirections.actionGroupListToDetallePelicula(movie)
            this@GroupList.findNavController().navigate(action)
        }
        recyclerAdapterLatest.setOnClickListener { view ->
            val pelicula = recyclerAdapterLatest.collection[binding.recyclerLatest.getChildAdapterPosition(view)]
            val action = GroupListDirections.actionGroupListToDetallePelicula(pelicula)
            this@GroupList.findNavController().navigate(action)
        }


        binding.textViewMoreLatest.setOnClickListener { view?.findNavController()?.navigate(R.id.listLatest) }
        binding.textViewMorePopulate.setOnClickListener { view?.findNavController()?.navigate(R.id.listPopulate) }
        binding.textViewMoreToprated.setOnClickListener { view?.findNavController()?.navigate(R.id.listTopRated) }

        recyclerAdapterTopRated.setOnClickListener { view ->
            val pelicula = recyclerAdapterTopRated.collection[binding.recyclerTopRated.getChildAdapterPosition(view)]
            val action = GroupListDirections.actionGroupListToDetallePelicula(pelicula)
            this@GroupList.findNavController().navigate(action)
        }
    }
    private fun loadListMovies() {
        viewModel.loadListTopRated()
        viewModel.loadListPopular()
        viewModel.loadListLatest()
    }
    private fun renderListTopRated(listMovies: List<Movie>?) {

        listMovies?.let {
            recyclerAdapterTopRated.collection = it
        }
    }
    private fun renderListPopular(listMovies: List<Movie>?) {

        listMovies?.let {
            recyclerAdapterPopular.collection = it
        }
    }
    private fun renderListLatest(listMovies: List<Movie>?) {

        listMovies?.let {
            recyclerAdapterLatest.collection = it
        }
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when(failure) {
            is Failure.CustomError -> renderFailure(failure.errorCode, failure.errorMessage)
            else -> renderFailure(0, "")
        }
    }

    private fun renderFailure(errorCode: Int, errorMessage: String?) {
        hideProgress()
        showError(errorCode, errorMessage, object : DialogCallback {
            override fun onAccept() {
                loadListMovies()
            }

            override fun onDecline() {
                onBackPressed()
            }
        })
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        val itemFavoritos = menu.findItem(R.id.itemFavorito)
        itemFavoritos.isVisible = false
    }
}