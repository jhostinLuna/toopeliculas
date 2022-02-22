package com.jhostinlh.topeliculas.features.vistaFragments.adaptadores


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.extensions.inflate
import com.jhostinlh.topeliculas.features.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.features.viewModel.ShareRepoViewModel
import kotlinx.android.synthetic.main.fragment_item.view.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class FavoritosAdapterRecyclerView(
    val viewModel: ShareRepoViewModel
): RecyclerView.Adapter<FavoritosAdapterRecyclerView.Holder>() {
    internal var collection: List<Pelicula> by Delegates.observable(emptyList()) {
            _, _, _ -> notifyDataSetChanged()
    }
    internal var clickListener: (Movie) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent.inflate(R.layout.fragment_item))


    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(collection.get(position).toMovie(),clickListener,viewModel)

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val whatsapp: ImageView = itemView.findViewById(R.id.imageView_whatsapp)

        fun bind(movie: Movie, clickListener:((Movie)-> Unit), viewModel: ShareRepoViewModel){

            itemView.setOnClickListener { clickListener(movie) }
            itemView.txt_titulo_item.text = movie.title
            itemView.rtingbar_item.rating = (movie.voteAverage.toFloat() * 5) / 10
            itemView.textViewDescription.text = movie.overview
            Glide.with(itemView).load(Data.URL_BASE_IMG + movie.posterPath)
                .into(itemView.img_portada_fr_item)
            itemView.txt_votos_fr_item.text = movie.voteCount.toString()
            if (movie.favorito == true) {
                itemView.imageButton_favorite.setImageResource(R.drawable.corazontrue)
            }
            itemView.imageButton_favorite.setOnClickListener {
                when(movie.favorito){
                    true-> {
                        movie.favorito = false

                        itemView.imageButton_favorite.setImageResource(R.drawable.corazonfalse)
                        viewModel.remove(movie)
                    }
                    false->{
                        movie.favorito = true

                        itemView.imageButton_favorite.setImageResource(R.drawable.corazontrue)
                        viewModel.addFavorito(movie)
                    }
                }
            }
            itemView.setOnClickListener { clickListener(movie) }
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
}