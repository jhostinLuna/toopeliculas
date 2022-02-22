package com.jhostinlh.topeliculas.features.vistaFragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.features.vistaFragments.adaptadores.GroupListRecyclerAdapter
import com.jhostinlh.topeliculas.databinding.FragmentGroupListBinding
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.extensions.failure
import com.jhostinlh.topeliculas.core.extensions.observe
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_group_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupList.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupList : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModel: ShareRepoViewModel by sharedViewModel()

    val recyclerAdapterTopRated: GroupListRecyclerAdapter= GroupListRecyclerAdapter()
    val recyclerAdapterPopular: GroupListRecyclerAdapter=GroupListRecyclerAdapter()
    val recyclerAdapterLatest: GroupListRecyclerAdapter=GroupListRecyclerAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
        with(viewModel){
            observe(listPopulate,::renderListPopular)
        }
        with(viewModel){
            observe(listTopRated,::renderListTopRated)
        }
        with(viewModel){
            observe(listLatest,::renderListLatest)
            failure(failure,::handleFailure)
        }
    }
    private fun renderListPopular(listPopular: List<Movie>?){
        recyclerAdapterPopular.collection = listPopular.orEmpty()
        hideProgress()
    }
    private fun renderListTopRated(listTopRated:List<Movie>?){
        recyclerAdapterTopRated.collection = listTopRated.orEmpty()
        hideProgress()
    }
    private fun renderListLatest(listTopLatest:List<Movie>?){
        recyclerAdapterLatest.collection = listTopLatest.orEmpty()
        hideProgress()
    }
    private fun initListeners(){
        recyclerAdapterPopular.clickListener = { movie ->
            val action = GroupListDirections.actionGroupListToDetallePelicula(movie)
            view?.findNavController()?.navigate(action)
        }
        recyclerAdapterTopRated.clickListener = {movie ->
            val action = GroupListDirections.actionGroupListToDetallePelicula(movie)
            view?.findNavController()?.navigate(action)
        }
        recyclerAdapterLatest.clickListener = {movie ->
            val action = GroupListDirections.actionGroupListToDetallePelicula(movie)
            view?.findNavController()?.navigate(action)
        }
        //Buttons Ver Más
        val bundle = Bundle()

        textView_more_latest.setOnClickListener {
            bundle.putString("nameList",Data.SERVICE_LATEST)
            view?.findNavController()?.navigate(R.id.listMovies,bundle)
        }
        textView_more_populate.setOnClickListener {
            bundle.putString("nameList",Data.SERVICE_POPULATE)
            view?.findNavController()?.navigate(R.id.listMovies,bundle)
        }
        textView_more_toprated.setOnClickListener {
            bundle.putString("nameList",Data.SERVICE_TOP_RATED)
            view?.findNavController()?.navigate(R.id.listMovies,bundle)
        }

    }

    private fun initializeViews(){
        setHasOptionsMenu(true)
        //Lista Top Rated
        recycler_toprated.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recycler_toprated.setHasFixedSize(true)
        recyclerAdapterTopRated.collection = viewModel.getTopRated().value.orEmpty()
        recycler_toprated.adapter = recyclerAdapterTopRated
        //Lista Popular
        recycler_popular.layoutManager= LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recycler_popular.setHasFixedSize(true)
        recyclerAdapterPopular.collection = viewModel.getListPopular().value.orEmpty()
        recycler_popular.adapter = recyclerAdapterPopular
        //Lista Latest
        recycler_latest.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.HORIZONTAL,false)
        recycler_latest.setHasFixedSize(true)
        recyclerAdapterLatest.collection = viewModel.getListLatest().value.orEmpty()
        recycler_latest.adapter = recyclerAdapterLatest

    }
    private fun handleFailure(failure: Failure?){
        viewModel.failure = MutableLiveData<Failure>()
        when(failure) {
            is Failure.NetworkConnection -> renderFailure(0,context?.resources?.getString(R.string.error_conection_internet))
            is Failure.CustomError -> renderFailure(failure.errorCode, context?.resources?.getString(R.string.error_server))
            else -> renderFailure(0, "¡Error desconocido intentalo mas tarde!!")
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

    private fun loadListMovies() {
        showProgress()
        val popular = viewModel.getListPopular().value
        val topRated = viewModel.getTopRated().value
        val latest = viewModel.getListLatest().value
        if (popular.isNullOrEmpty() || topRated.isNullOrEmpty() || latest.isNullOrEmpty()){
            viewModel.getListMovies(Data.SERVICE_POPULATE)
            viewModel.getListMovies(Data.SERVICE_TOP_RATED)
            viewModel.getListMovies(Data.SERVICE_LATEST)
        }
    }


    override fun layoutId(): Int = R.layout.fragment_group_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadListMovies()
        initializeViews()
        initListeners()



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