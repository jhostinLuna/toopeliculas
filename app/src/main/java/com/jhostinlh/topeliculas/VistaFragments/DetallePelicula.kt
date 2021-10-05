package com.jhostinlh.topeliculas.VistaFragments

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.ViewModel.DetallePeliculaViewModel
import com.jhostinlh.topeliculas.ViewModel.PeliculaViewModelFactory
import com.jhostinlh.topeliculas.databinding.FragmentDetallePeliculaBinding
import com.jhostinlh.topeliculas.topRatedAplication


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetallePelicula.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetallePelicula : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var pelicula: Pelicula
    private val safeArgs: DetallePeliculaArgs by navArgs()
    val viewModel: DetallePeliculaViewModel by activityViewModels() {
        PeliculaViewModelFactory((context?.applicationContext as topRatedAplication).repository,safeArgs.peli)
    }
    lateinit var binding: FragmentDetallePeliculaBinding
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        pelicula = safeArgs.peli

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePeliculaBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        Log.i("peli",pelicula.toString())
        viewModel.getListTrailer().observe(viewLifecycleOwner,
            object : Observer<List<ResultTrailer>> {
                override fun onChanged(listaTrailers: List<ResultTrailer>?) {
                    if (!listaTrailers.isNullOrEmpty()) {
                        binding.imageViewVerTrailer.setOnClickListener {
                            watchYoutubeVideo(listaTrailers.get(0).key)
                        }
                    }else{
                        binding.imageViewVerTrailer.setImageResource(R.drawable.llorando)
                        binding.textViewVerTrailer.text = "Lo siento no hemos encontrado ningun trailer disponible."
                    }


                }

            })



        binding.txtDescriptionFrDetallepelicula!!.text = pelicula.overview
        binding.txtTrailerFrDetallepelicula!!.text = pelicula.title
        binding.txtIdiomaFrDetallepelicula!!.text = pelicula.originalLanguage
        binding.txtTitleoriginFrDetallepelicula!!.text = pelicula.originalTitle
        binding.txtFechapublicacionFrDetallepelicula!!.text = pelicula.releaseDate
        Glide.with(binding.root).load(Data.URL_BASE_IMG +pelicula.backdropPath).into(binding.imageViewDetallePelicula)


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
/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(com.jhostinlh.topeliculas.R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

 */

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
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}