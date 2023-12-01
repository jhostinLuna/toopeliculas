package com.jhostinlh.topeliculas.vistaFragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.databinding.FragmentDetallePeliculaBinding
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.extensions.observe
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import com.jhostinlh.topeliculas.modelo.entitys.ResultTrailer
import com.jhostinlh.topeliculas.viewModel.FavoritesMoviesUseCase
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import dagger.hilt.android.AndroidEntryPoint



/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class DetallePelicula : BaseFragment() {
    lateinit var movie:Movie
    private val safeArgs: DetallePeliculaArgs by navArgs()
    val viewModel: ShareRepoViewModel by activityViewModels()
    private lateinit var binding: FragmentDetallePeliculaBinding
    private var listResultTrailer: List<ResultTrailer> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        movie = safeArgs.peli
        viewModel.loadTrailers(movie.id)
        with(viewModel) {
            observe(getListTrailers(), ::renderListTrailer)
            observe(failure, ::handleFailure)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePeliculaBinding.inflate(inflater,container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        initializeListeners()
    }
    private fun initializeView(){
        binding.txtDescriptionFrDetallepelicula.text = movie.overview
        binding.txtTrailerFrDetallepelicula.text = movie.title
        binding.txtIdiomaFrDetallepelicula.text = movie.originalLanguage
        binding.txtTitleoriginFrDetallepelicula.text = movie.originalTitle
        binding.txtFechapublicacionFrDetallepelicula.text = movie.releaseDate
        Glide.with(binding.root).load(Data.URL_BASE_IMG + movie.backdropPath).into(binding.imageViewDetallePelicula)
    }
    private fun initializeListeners(){
        binding.imageViewVerTrailer.setOnClickListener {
            watchYoutubeVideo()
        }
        binding.imageViewPlay.setOnClickListener {
            watchYoutubeVideo()
        }
    }
    private fun renderListTrailer(listResultTrailer: List<ResultTrailer>?) {
        listResultTrailer?.let {
            this.listResultTrailer = it
            if (listResultTrailer.isEmpty()) {
                binding.imageViewVerTrailer.setImageResource(R.drawable.llorando)
                binding.textViewVerTrailer.text = getString(R.string.not_video)
                binding.imageViewPlay.visibility = View.INVISIBLE
            }
        }
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
                viewModel.loadTrailers(movie.id)
            }

            override fun onDecline() {
                onBackPressed()
            }
        })
    }
    private fun changeFavorito(itemFavorito: MenuItem){
        when(movie.favorito){
            true -> {
                viewModel.operationDbFavorite(FavoritesMoviesUseCase.DELETE,movie.toPeliculaEntity())
                movie.favorito = false
                itemFavorito.icon = context?.getDrawable(R.drawable.ic_baseline_favorite_24_normal)
            }
            false -> {
                movie.favorito = true
                viewModel.operationDbFavorite(FavoritesMoviesUseCase.ADD,movie.toPeliculaEntity())
                itemFavorito.icon = context?.getDrawable(R.drawable.ic_baseline_favorite_24_selected)
            }
        }
    }

    private fun watchYoutubeVideo() {
        if (listResultTrailer.isNotEmpty()) {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${listResultTrailer[0].key}"))
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=${listResultTrailer[0].key}")
            )
            try {
                context?.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context?.startActivity(webIntent)
            }
        }else {
            viewModel.loadTrailers(movie.id)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val itemFavorito = menu.findItem(R.id.itemFavorito)
        itemFavorito.isVisible = true
        if (movie.favorito){
            itemFavorito.icon = context?.getDrawable(R.drawable.ic_baseline_favorite_24_selected)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.itemFavorito){
            changeFavorito(item)
        }
        // devuelve si el comportamiento tiene que ser normal(false) o no(true)
        return false
    }

}