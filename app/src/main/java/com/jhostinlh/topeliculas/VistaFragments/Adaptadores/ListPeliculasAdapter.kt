package com.jhostinlh.topeliculas.VistaFragments.Adaptadores


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jhostinlh.topeliculas.Data
import com.jhostinlh.topeliculas.Modelo.Entitys.Pelicula
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.VistaFragments.ListTopRatedDirections
import com.jhostinlh.topeliculas.ViewModel.ShareRepoViewModel


class ListPeliculasAdapter constructor(
    var listTopRated: List<Pelicula>,
    val context: Fragment?,
    val viewModel: ShareRepoViewModel
): RecyclerView.Adapter<ListPeliculasAdapter.Holder>() {


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
        Glide.with(holder.itemView).load(Data.URL_BASE_IMG +listTopRated[position].posterPath).into(holder.imgPeli)
        holder.votos.text = ""+listTopRated[position].voteCount
        if (listTopRated[position].favorito==true){
            holder.favorito.setImageResource(R.drawable.corazontrue)
        }

        holder.itemView.setOnClickListener {
            val pelicula = listTopRated[position]
            val action = ListTopRatedDirections.actionListTopRatedToDetallePelicula(pelicula)

            context?.findNavController()?.navigate(action)
        }

        holder.favorito.setOnClickListener {
            if (listTopRated[position].favorito){
                listTopRated[position].favorito = false
                holder.favorito.setImageResource(R.drawable.corazonfalse)
            }else{
                listTopRated[position].favorito = true
                holder.favorito.setImageResource(R.drawable.corazontrue)
            }
            viewModel.addFavorito(listTopRated[position])

        }
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

    }

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

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txt_titulo_item_fr_toprated)
        val ratinBar: RatingBar = itemView.findViewById(R.id.rtingbar_item_fr_top_rated)
        val imgPeli: ImageView = itemView.findViewById(R.id.img_portada_fr_item_toprated)
        val votos: TextView = itemView.findViewById(R.id.txt_votos_fr_item_toprated)
        var favorito: ImageButton = itemView.findViewById(R.id.imageButton_favorito_itemtoprated)
        val whatsapp: ImageView = itemView.findViewById(R.id.imageView_whatsapp)
    }

    fun setFiltro(lista:ArrayList<Pelicula>){
        this.listTopRated = listOf()
        listTopRated = lista
        notifyDataSetChanged()
    }

}