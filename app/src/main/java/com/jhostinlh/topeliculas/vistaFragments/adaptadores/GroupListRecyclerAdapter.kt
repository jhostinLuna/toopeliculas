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
import kotlin.properties.Delegates

class GroupListRecyclerAdapter :RecyclerView.Adapter<GroupListRecyclerAdapter.Holder>(),View.OnClickListener {
    internal var collection: List<Movie> by Delegates.observable(emptyList()) {
            _, _, _ -> notifyDataSetChanged()
    }
    private lateinit var  listenerOfFragment: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group_list, parent, false)
        view.setOnClickListener(this)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(holder.itemView).load(Data.URL_BASE_IMG +collection[position].posterPath).into(holder.portada)

    }

    override fun getItemCount(): Int {
        return collection.size
    }

    class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val portada:ImageView = itemView.findViewById(R.id.imageview_group_list)

    }

    override fun onClick(v: View?) {
        listenerOfFragment.onClick(v)
    }
    fun setOnClickListener(listener: View.OnClickListener){
        listenerOfFragment = listener
    }

}