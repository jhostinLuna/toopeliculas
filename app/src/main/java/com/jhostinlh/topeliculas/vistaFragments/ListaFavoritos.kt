package com.jhostinlh.topeliculas.vistaFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.extensions.observe
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.FavoritosAdapterRecyclerView
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.databinding.FragmentListaFavoritosBinding
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.viewModel.FavoritesMoviesUseCase
import com.jhostinlh.topeliculas.vistaFragments.adaptadores.ClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class ListaFavoritos : BaseFragment() {

    private lateinit var topRatedRecycler: RecyclerView
    val viewModel: ShareRepoViewModel by activityViewModels()
    private lateinit var binding: FragmentListaFavoritosBinding
    @Inject
    lateinit var favoritesAdapter: FavoritosAdapterRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel){
            observe(getFavorites(),::renderListFavorites)
            observe(failure,::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaFavoritosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
        initializeListeners()
    }
    private fun initializeView(){
        topRatedRecycler = binding.topRatedRecyclerLf
        topRatedRecycler.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,false)
        topRatedRecycler.setHasFixedSize(true)
        topRatedRecycler.adapter = favoritesAdapter

    }
    private fun initializeListeners(){
        binding.editTextTitulopeliculaLf
            .addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    favoritesAdapter.setFiltro(viewModel.listaFiltrada(s.toString()))
                }

                override fun afterTextChanged(s: Editable) {}
            })
        favoritesAdapter.setOnclickListener(object : ClickListener{
            override fun onItemClick(movie: Movie, view: View) {
                val action = ListaFavoritosDirections.actionListaFavoritosToDetallePelicula(movie)
                view.findNavController().navigate(action)
            }

            override fun onFavoriteIcon(movie: Movie, view: View) {
                viewModel.operationDbFavorite(FavoritesMoviesUseCase.DELETE,movie.toPeliculaEntity())
                movie.favorito = false
                view.findViewById<ImageButton>(R.id.imageButton_favorito_itemtoprated).setImageResource(R.drawable.corazonfalse)
                val newList = favoritesAdapter.collection.toMutableList()
                val indexOf = favoritesAdapter.collection.find { it.id == movie.id}
                newList.remove(indexOf)
                favoritesAdapter.collection = newList
            }

        })
    }
    private fun renderListFavorites(listMovies: List<Pelicula>?) {
        listMovies?.let { favoritesAdapter.collection = it }

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
            }

            override fun onDecline() {
                onBackPressed()
            }
        })
    }
}