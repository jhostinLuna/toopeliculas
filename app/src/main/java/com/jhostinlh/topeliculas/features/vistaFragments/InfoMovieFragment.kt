package com.jhostinlh.topeliculas.features.vistaFragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.core.exception.Failure
import com.jhostinlh.topeliculas.core.extensions.failure
import com.jhostinlh.topeliculas.core.extensions.observe
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.core.platform.BaseFragment
import com.jhostinlh.topeliculas.features.modelo.entitys.ResultTrailer
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import kotlinx.android.synthetic.main.fragment_detalle_pelicula.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.coroutines.CoroutineContext


// TODO: Rename parameter arguments, choose names that match
private const val BUNDLE = "movieArgs"

/**
 * A simple [Fragment] subclass.
 * Use the [DetallePelicula.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetallePelicula : BaseFragment(),CoroutineScope,View.OnClickListener {
    lateinit var movie:Movie
    private val safeArgs: DetallePeliculaArgs by navArgs()
    val viewModel: ShareRepoViewModel by sharedViewModel()
    var checkLoadTrailer: Boolean = false
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        movie = safeArgs.peli
        job = Job()
        with(viewModel){
            observe(resultTrailers,::renderResultTrailers)
        }
        with(viewModel){
            observe(mensaje,::renderMessageDatabase)
            failure(failure,::handleFailure)
        }

    }
    private fun loadTrailer(language:String){
        showProgress()
        viewModel.loadTrailers(movie.id,language)
    }
    private fun renderResultTrailers(listTrailer: List<ResultTrailer>?){
        if(!checkLoadTrailer && listTrailer.isNullOrEmpty()){
            loadTrailer("en")
            checkLoadTrailer = true
            return
        }
        if (listTrailer.isNullOrEmpty()) {
            imageView_verTrailer.setImageResource(R.drawable.llorando)
            textView_verTrailer.text = context?.getString(R.string.not_found_trailer)
            imageView_verTrailer.isEnabled = false
            imageView_play.visibility = View.INVISIBLE

        }else{
            imageView_verTrailer.setImageResource(R.drawable.youtube)
            textView_verTrailer.text = context?.getString(R.string.found_trailer)
        }
        hideProgress()
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
                loadTrailer("es")
            }

            override fun onDecline() {
                onBackPressed()
            }
        })
    }

    private fun initializeViews(){
        txt_description_fr_detallepelicula.text = movie.overview
        txt_trailer_fr_detallepelicula.text = movie.title
        txt_idioma_fr_detallepelicula.text = movie.originalLanguage
        txt_titleorigin_fr_detallepelicula.text = movie.originalTitle
        txt_fechapublicacion_fr_detallepelicula.text = movie.releaseDate
        view?.let { Glide.with(it).load(Data.URL_BASE_IMG + movie.backdropPath).into(imageView_detallePelicula) }
    }
    private fun initListeners(){
        imageView_verTrailer.setOnClickListener(this)
        imageView_play.setOnClickListener(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTrailer("es")
        initializeViews()
        initListeners()

    }
    private fun addMovie(){
        showProgress()
        viewModel.addFavorito(movie)
    }
    private fun renderMessageDatabase(message: String?){
        hideProgress()
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()

    }
    private fun removeMovie(){
        showProgress()
        viewModel.remove(movie)
    }
    private fun handleFailureDatabase(failure: Failure?){
        when(failure) {
            is Failure.CustomError -> Toast.makeText(context,failure.errorMessage,Toast.LENGTH_LONG).show()
            else -> Toast.makeText(context,"Ha Ocurrido un Error Vuelve a intentarlo mas tarde!!",Toast.LENGTH_LONG).show()
        }
    }

    fun changeFavorito(itemFavorito: MenuItem){
        when(movie.favorito){
            true -> {
                movie.favorito = false
                removeMovie()
                itemFavorito.icon = context?.getDrawable(R.drawable.ic_baseline_favorite_24_normal)
            }
            false -> {
                movie.favorito = true
                addMovie()
                itemFavorito.icon = context?.getDrawable(R.drawable.ic_baseline_favorite_24_selected)
            }
        }
    }

    override fun layoutId(): Int = R.layout.fragment_detalle_pelicula

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

    override fun onClick(view: View?) {
        when(view){
            imageView_play -> viewModel.resultTrailers.value?.get(0)?.let { it1 -> watchYoutubeVideo(it1.key) }
            imageView_verTrailer -> viewModel.resultTrailers.value?.get(0)?.let { it1 -> watchYoutubeVideo(it1.key) }
        }
    }
}