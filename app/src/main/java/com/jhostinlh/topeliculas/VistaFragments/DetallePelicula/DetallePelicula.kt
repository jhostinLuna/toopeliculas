package com.jhostinlh.topeliculas.VistaFragments.DetallePelicula

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.ResultTrailer
import com.jhostinlh.topeliculas.databinding.FragmentDetallePeliculaBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


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
    lateinit var viewModel: DetallePeliculaViewModel
    lateinit var binding: FragmentDetallePeliculaBinding
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val safeArgs: DetallePeliculaArgs by navArgs()
        pelicula = safeArgs.peli
        viewModel = ViewModelProvider(this,
            PeliculaViewModelFactory(activity!!.application,pelicula)
        ).get(
            DetallePeliculaViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePeliculaBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        Log.i("peli",pelicula.toString())
        lifecycle.addObserver(binding.videoTrailerFrDetallepelicula)
        viewModel.getListTrailer().observe(viewLifecycleOwner,
            object : Observer<List<ResultTrailer>>{
                override fun onChanged(listaTrailers: List<ResultTrailer>?) {
                    if (!listaTrailers.isNullOrEmpty()) {
                        binding.videoTrailerFrDetallepelicula.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    val videoId = listaTrailers[0].key
                                    youTubePlayer.loadVideo(videoId, 0f)
                                }
                            })
                    }else{
                        Toast.makeText(activity?.applicationContext,"Lo siento pero no hemos encontrado Trailers disponibles",Toast.LENGTH_LONG).show()

                    }


                }

            })



        binding.txtDescriptionFrDetallepelicula!!.text = pelicula.overview
        binding.txtTrailerFrDetallepelicula!!.text = pelicula.title
        binding.txtIdiomaFrDetallepelicula!!.text = pelicula.originalLanguage
        binding.txtTitleoriginFrDetallepelicula!!.text = pelicula.originalTitle
        binding.txtFechapublicacionFrDetallepelicula!!.text = pelicula.releaseDate

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(com.jhostinlh.topeliculas.R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
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
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}