package com.jhostinlh.topeliculas.vistaFragments.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie

class GroupListRecyclerAdapter constructor(
    val listPathImgPortadas: List<Movie>
    ): RecyclerView.Adapter<GroupListRecyclerAdapter.Holder>(),View.OnClickListener {

    private lateinit var  listenerOfFragment: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group_list, parent, false)
        view.setOnClickListener(this)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(holder.itemView).load(Data.URL_BASE_IMG +listPathImgPortadas[position].posterPath).into(holder.portada)

    }

    override fun getItemCount(): Int {
        return listPathImgPortadas.size
    }

    class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val portada:ImageView = itemView.findViewById(R.id.imageview_group_list)

    }

    override fun onClick(v: View?) {
        if (listenerOfFragment!= null) listenerOfFragment.onClick(v)
    }
    fun setOnClickListener(listener: View.OnClickListener){
        listenerOfFragment = listener
    }

}