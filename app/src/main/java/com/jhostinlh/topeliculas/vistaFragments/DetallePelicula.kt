package com.jhostinlh.topeliculas.vistaFragments

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.databinding.FragmentDetallePeliculaBinding
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.Aplication
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


// TODO: Rename parameter arguments, choose names that match
private const val BUNDLE = "movieArgs"

/**
 * A simple [Fragment] subclass.
 * Use the [DetallePelicula.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetallePelicula : Fragment(),CoroutineScope {
    lateinit var movie:Movie
    private val safeArgs: DetallePeliculaArgs by navArgs()
    val viewModel: ShareRepoViewModel by activityViewModels {
        ShareRepoViewModelFactory((context?.applicationContext as Aplication).repository)
    }
    lateinit var binding: FragmentDetallePeliculaBinding

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private lateinit var job: Job
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        movie = safeArgs.peli
        job = Job()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (movie != null){
            Log.i("peli",movie.toString())
            launch {
                val trailer = viewModel.loadTrailers(movie.id)
                if (!trailer.isNullOrEmpty()) {
                    binding.imageViewVerTrailer.setOnClickListener {
                        watchYoutubeVideo(trailer.get(0).key)
                    }
                    binding.imageViewPlay.setOnClickListener {
                        watchYoutubeVideo(trailer.get(0).key)
                    }
                }else{
                    binding.imageViewVerTrailer.setImageResource(R.drawable.llorando)
                    binding.textViewVerTrailer.text = "Lo siento no hemos encontrado ningun trailer disponible."
                    binding.imageViewPlay.visibility = View.INVISIBLE
                }
            }

            binding.txtDescriptionFrDetallepelicula.text = movie.overview
            binding.txtTrailerFrDetallepelicula.text = movie.title
            binding.txtIdiomaFrDetallepelicula.text = movie.originalLanguage
            binding.txtTitleoriginFrDetallepelicula.text = movie.originalTitle
            binding.txtFechapublicacionFrDetallepelicula.text = movie.releaseDate
            Glide.with(binding.root).load(Data.URL_BASE_IMG + movie.backdropPath).into(binding.imageViewDetallePelicula)


        }else{
            findNavController().navigateUp()
        }

    }
    fun changeFavorito(itemFavorito: MenuItem){
        when(movie.favorito){
            true -> {
                viewModel.remove(movie.toPeliculaEntity())
                movie.favorito = false
                itemFavorito.icon = context?.getDrawable(R.drawable.ic_baseline_favorite_24_normal)
            }
            false -> {
                movie.favorito = true
                viewModel.addFavorito(movie.toPeliculaEntity())
                itemFavorito.icon = context?.getDrawable(R.drawable.ic_baseline_favorite_24_selected)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePeliculaBinding.inflate(inflater,container,false)
        return binding.root

    }
    fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            context?.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context?.startActivity(webIntent)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetallePelicula.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetallePelicula().apply {
                arguments = Bundle().apply {
                }
            }
    }
}