package com.jhostinlh.topeliculas.vistaFragments.adaptadores


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.modelo.entitys.Pelicula
import com.jhostinlh.topeliculas.modelo.retrofit.dataRemote.Movie
import com.jhostinlh.topeliculas.viewModel.ShareRepoViewModel
import com.jhostinlh.topeliculas.vistaFragments.ListaFavoritosDirections
import kotlin.collections.ArrayList


class FavoritosAdapterRecyclerView(
    var listTopRated: List<Pelicula>,
    val context: Context?,
    val viewModel: ShareRepoViewModel
): RecyclerView.Adapter<FavoritosAdapterRecyclerView.Holder>() {
    internal var clickListener: (Movie) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int {
        return listTopRated.size
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(listTopRated.get(position).toMovie(),clickListener,viewModel)

    }
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txt_titulo_item_fr_toprated)
        val ratinBar: RatingBar = itemView.findViewById(R.id.rtingbar_item_fr_top_rated)
        val imgPeli: ImageView = itemView.findViewById(R.id.img_portada_fr_item_toprated)
        val votos: TextView = itemView.findViewById(R.id.txt_votos_fr_item_toprated)
        var favorito:ImageButton = itemView.findViewById(R.id.imageButton_favorito_itemtoprated)
        val description = itemView.findViewById<TextView>(R.id.textViewDescription)
        //val whatsapp: ImageView = itemView.findViewById(R.id.imageView_whatsapp)

        fun bind(listTopRated: Movie,clickListener:((Movie)-> Unit),viewModel: ShareRepoViewModel){

            itemView.setOnClickListener { clickListener(listTopRated) }
            txtTitulo.text = listTopRated.title
            ratinBar.rating = (listTopRated.voteAverage.toFloat() * 5) / 10
            description.text = listTopRated.overview
            Glide.with(itemView).load(Data.URL_BASE_IMG + listTopRated.posterPath)
                .into(imgPeli)
            votos.text = listTopRated.voteCount.toString()
            if (listTopRated.favorito == true) {
                favorito.setImageResource(R.drawable.corazontrue)
            }
            itemView.setOnClickListener {
                val action = ListaFavoritosDirections.actionListaFavoritosToDetallePelicula(listTopRated)

                itemView.findNavController().navigate(action)
            }
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
            favorito.setOnClickListener {
                when(listTopRated.favorito){
                    true-> {
                        listTopRated.favorito = false

                        favorito.setImageResource(R.drawable.corazonfalse)
                        viewModel.remove(listTopRated.toPeliculaEntity())
                    }
                    false->{
                        listTopRated.favorito = true

                        favorito.setImageResource(R.drawable.corazontrue)
                        viewModel.addFavorito(listTopRated.toPeliculaEntity())
                    }
                }
            }


            /*
            if (context != null) {
                Toast.makeText(
                    context.context,
                    "position = ${position} -- tamaño de lista: -> " + listTopRated.size,
                    Toast.LENGTH_LONG
                ).show()
            }
            notifyDataSetChanged()
            notifyItemRemoved(position)

             */





        }


    }
    fun setFiltro(lista:ArrayList<Pelicula>){
        this.listTopRated = listOf()
        listTopRated = lista
        notifyDataSetChanged()
    }
}