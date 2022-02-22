package com.jhostinlh.topeliculas.features.vistaFragments.adaptadores


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.extensions.inflate
import com.jhostinlh.topeliculas.features.modelo.retrofit.dataRemote.Movie
import kotlinx.android.synthetic.main.fragment_item.view.*
import kotlin.properties.Delegates


class ListMovieAdapter: RecyclerView.Adapter<ListMovieAdapter.Holder>() {
    internal var collection: List<Movie> by Delegates.observable(emptyList()){
        _,_,_ -> notifyDataSetChanged()
    }
    internal var clickListener: (Movie) -> Unit = {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent.inflate(R.layout.fragment_item))

    override fun getItemCount(): Int {
        return collection.size
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(collection[position],clickListener)

        }

/*
//compartir pelicula por whatsapp
        holder.whatsapp.setOnClickListener {
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, listTopRated[position].overview)
                putExtra(Intent.EXTRA_TITLE,listTopRated[position].originalTitle)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, "titulo de prueba")
            context?.startActivity(shareIntent)
        }

 */

/*
    fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            context?.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context?.startActivity(webIntent)
        }
    }

 */

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val whatsapp: ImageView = itemView.findViewById(R.id.imageView_whatsapp)
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            itemView.txt_titulo_item.text = movie.title
            itemView.rtingbar_item.rating = (movie.voteAverage.toFloat() * 5) / 10
            Glide.with(itemView).load(Data.URL_BASE_IMG + movie.posterPath)
                .into(itemView.img_portada_fr_item)
            itemView.txt_votos_fr_item.text = "" + movie.voteCount
            itemView.imageButton_favorite.visibility = View.INVISIBLE
            itemView.textViewDescription.text = movie.overview
            itemView.setOnClickListener { clickListener(movie) }

        }
    }


}