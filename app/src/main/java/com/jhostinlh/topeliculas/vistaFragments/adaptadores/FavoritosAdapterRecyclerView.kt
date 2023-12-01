package com.jhostinlh.topeliculas.vistaFragments.adaptadores


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.databinding.FragmentItemBinding
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class FavoritosAdapterRecyclerView @Inject constructor(
    @ApplicationContext val context: Context?
): RecyclerView.Adapter<FavoritosAdapterRecyclerView.Holder>(),View.OnClickListener {
    private lateinit var customClickListener: ClickListener
    internal var collection: List<Pelicula> by Delegates.observable(emptyList()) {
            _, _, _ -> notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }


    override fun getItemCount(): Int {
        return collection.size
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(collection[position].toMovie(),customClickListener)

    }
    class Holder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        //val whatsapp: ImageView = itemView.findViewById(R.id.imageView_whatsapp)

        fun bind(
            listTopRated: Movie,
            customClickListener: ClickListener
        ){

            itemView.setOnClickListener { customClickListener.onItemClick(listTopRated,it) }
            binding.txtTituloItemFrToprated.text = listTopRated.title
            binding.rtingbarItemFrTopRated.rating = (listTopRated.voteAverage.toFloat() * 5) / 10
            binding.textViewDescription.text = listTopRated.overview
            Glide.with(itemView).load(Data.URL_BASE_IMG + listTopRated.posterPath)
                .into(binding.imgPortadaFrItemToprated)
            binding.txtVotosFrItemToprated.text = listTopRated.voteCount.toString()
            binding.imageButtonFavoritoItemtoprated.setImageResource(R.drawable.corazontrue)
            binding.imageButtonFavoritoItemtoprated.setOnClickListener { customClickListener.onFavoriteIcon(listTopRated,itemView) }
            /*
            // compartir pelicula por whatsapp
            whatsapp.setOnClickListener {

                val intent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, listTopRated.overview)
                    putExtra(Intent.EXTRA_TITLE,listTopRated.title)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "titulo de prueba")
                context?.startActivity(shareIntent)
            }
             */

        }


    }
    fun setFiltro(lista:ArrayList<Pelicula>){
        this.collection = listOf()
        collection = lista
        notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
    fun setOnclickListener(clickListener: ClickListener){
        customClickListener = clickListener
    }

}
interface ClickListener {
    fun onItemClick(movie: Movie,view: View)
    fun onFavoriteIcon(movie: Movie, view: View)
}