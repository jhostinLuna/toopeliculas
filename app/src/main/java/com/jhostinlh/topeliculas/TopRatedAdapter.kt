package com.jhostinlh.topeliculas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula

class TopRatedAdapter constructor(
val listTopRated: List<Pelicula>
): RecyclerView.Adapter<TopRatedAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, null, false)
        return Holder(view)
    }


    override fun getItemCount(): Int {
        return listTopRated.size
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.txtTitulo.text = listTopRated[position].title
        holder.ratinBar.rating = (listTopRated[position].voteAverage.toFloat()*5)/10
        val nombreImagenPortada =  listTopRated[position].posterPath
        Glide.with(holder.itemView).load(Data.URL_BASE_IMG+nombreImagenPortada).into(holder.imgPeli)
        holder.imgPeli.setImageResource(R.drawable.avengers)
        holder.votos.text = ""+listTopRated[position].voteCount
    }
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txt_titulo_item_fr_toprated)
        val ratinBar: RatingBar = itemView.findViewById(R.id.rtingbar_item_fr_top_rated)
        val imgPeli: ImageView = itemView.findViewById(R.id.img_portada_fr_item_toprated)
        val votos: TextView = itemView.findViewById(R.id.txt_votos_fr_item_toprated)
    }
}