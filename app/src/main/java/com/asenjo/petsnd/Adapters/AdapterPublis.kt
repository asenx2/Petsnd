package com.asenjo.petsnd.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.Views.Detail
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rowpub.view.*
import java.util.*

/**
 * Created by asenjo on 26/03/2018.
 */
//Clase adaptador para el recycler view de las publicaciones en Mainrv
//Se muestra la imagen, el nombre del autor y el título de la publicación
class AdapterPublis(val context: Context,
                    val layout: Int,
                    val dataList: ArrayList<Publicacion>
) : RecyclerView.Adapter<AdapterPublis.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {

        fun bind(dataItem: Publicacion, position: Int) {

            val url = dataItem.urlimage
            Picasso.with(context).load(url).into(itemView.ivrowpub)

            itemView.tvtitulo.text = dataItem.titulo
            itemView.tvrowupl.text = dataItem.uploader

            itemView.setOnClickListener(View.OnClickListener {
                onItemClick(dataItem)
            })

        }

        //Al pulsar en una publicación viajo a la vista detalle de la misma, donde se muestran sus comentarios,
        //se puede comentar y se puede marcar como favorito
        //Incluyo en el intent el objeto Publicacion para recogerlo en la vista detalle. Está serializado en el model
        private fun onItemClick(dataItem: Publicacion) {
            val intent = Intent(context, Detail::class.java)
            intent.putExtra("publipulsada", dataItem)
            context.startActivity(intent)
        }

    }

}