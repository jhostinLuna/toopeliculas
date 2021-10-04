package com.jhostinlh.topeliculas.VistaFragments.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.VistaFragments.ListTopRated.ListTopRatedDirections

class Adapter_recycler_group_list constructor(
    var listImgPortada: List<Pelicula>,
    val context: Fragment?
): RecyclerView.Adapter<Adapter_recycler_group_list.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, null, false)
        return Holder(view)
    }


    override fun getItemCount(): Int {
        return 5
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {


        Glide.with(holder.itemView).load(Data.URL_BASE_IMG +listImgPortada[position].posterPath).into(holder.imgPeli)



        holder.itemView.setOnClickListener {
            val pelicula = listImgPortada[position]
            val action = ListTopRatedDirections.actionListTopRatedToDetallePelicula(pelicula)

            if (context != null) {
                context.findNavController().navigate(action)
            }
        }
    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPeli: ImageView = itemView.findViewById(R.id.imageview_item_1)
    }

    fun setFiltro(lista:ArrayList<Pelicula>){
        this.listImgPortada = listOf()
        listImgPortada = lista
        notifyDataSetChanged()
    }

}