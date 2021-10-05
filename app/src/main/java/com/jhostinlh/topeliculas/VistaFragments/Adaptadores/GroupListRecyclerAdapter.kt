package com.jhostinlh.topeliculas.VistaFragments.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.R

class GroupListRecyclerAdapter constructor(
    val listPathImgPortadas: List<Pelicula>
    ): RecyclerView.Adapter<GroupListRecyclerAdapter.Holder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group_list, null, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(holder.itemView).load(Data.URL_BASE_IMG +listPathImgPortadas[position].posterPath).into(holder.portada)

    }

    override fun getItemCount(): Int {
        return 5
    }
    class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val portada:ImageView = itemView.findViewById(R.id.imageview_group_list)

    }

}