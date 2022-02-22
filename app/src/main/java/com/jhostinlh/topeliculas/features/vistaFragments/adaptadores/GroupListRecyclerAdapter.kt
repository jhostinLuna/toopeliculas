package com.jhostinlh.topeliculas.features.vistaFragments.adaptadores

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.extensions.inflate
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import kotlinx.android.synthetic.main.item_group_list.view.*
import kotlin.properties.Delegates

class GroupListRecyclerAdapter: RecyclerView.Adapter<GroupListRecyclerAdapter.Holder>(),View.OnClickListener {
    internal var collection: List<Movie> by Delegates.observable(emptyList()) {
            _, _, _ -> notifyDataSetChanged()
    }
    internal var clickListener: (Movie) -> Unit = { }
    private lateinit var  listenerOfFragment: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        /*
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group_list, parent, false)

         */
        return Holder(parent.inflate(R.layout.item_group_list))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(collection[position], clickListener)
        //Glide.with(holder.itemView).load(Data.URL_BASE_IMG +listPathImgPortadas[position].posterPath).into(holder.portada)

    }

    override fun getItemCount(): Int {
        return collection.size
    }

    class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        //val portada:ImageView = itemView.findViewById(R.id.imageview_group_list)
        fun bind(movie: Movie, clickListener: (Movie) -> Unit){
            Glide.with(itemView).load(Data.URL_BASE_IMG +movie.posterPath).into(itemView.imageview_group_list)
            itemView.setOnClickListener { clickListener(movie) }
        }
    }

    override fun onClick(v: View?) {
        if (listenerOfFragment!= null) listenerOfFragment.onClick(v)
    }
    fun setOnClickListener(listener: View.OnClickListener){
        listenerOfFragment = listener
    }

}