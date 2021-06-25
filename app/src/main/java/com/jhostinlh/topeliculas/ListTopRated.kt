package com.jhostinlh.topeliculas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhostinlh.tiempokotlin.Retrofit.MyApiAdapter
import com.jhostinlh.tiempokotlin.Retrofit.MyApiService
import com.jhostinlh.topeliculas.Modelo.Dao.PelisDao
import com.jhostinlh.topeliculas.Modelo.Database.AppDataBase
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.Modelo.Entitys.TopRated
import com.jhostinlh.topeliculas.Modelo.Repository.ImplementPelisRepository
import com.jhostinlh.topeliculas.Modelo.Repository.PelisRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListTopRated.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListTopRated : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var topRatedRecycler: RecyclerView
    lateinit var viewModel: ListTopRatedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        viewModel = ViewModelProvider(this).get(ListTopRatedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_list_top_rated, container, false)

        topRatedRecycler = view.findViewById(R.id.top_rated_recycler)
        topRatedRecycler.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)


        viewModel.getTopRated().observe(viewLifecycleOwner, Observer<List<Pelicula>>{ listTopRated ->
            val recyclerAdapter: TopRatedAdapter? = TopRatedAdapter(listTopRated)
            
            topRatedRecycler.adapter = recyclerAdapter
        })

        Log.i("listaPelis","Tiene: "+ (viewModel.getTopRated().value?.get(0)?.backdropPath ?: emptyList<Pelicula>()))



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListTopRated.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListTopRated().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}