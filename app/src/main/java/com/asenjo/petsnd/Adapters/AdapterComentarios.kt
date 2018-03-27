package com.asenjo.petsnd.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asenjo.petsnd.Model.Comentario
import kotlinx.android.synthetic.main.rowcom.view.*

/**
 * Created by asenjo on 26/03/18.
 */
//Clase adaptador para el recycler view de los comentarios dentro de las vista detalle
//En esta clase sólo hay que asignar valores a los tres textview dentro del cardview de comentario
class AdapterComentarios(val context: Context,
                         val layout: Int,
                         val dataList: List<Comentario>
                    ): RecyclerView.Adapter<AdapterComentarios.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {

        fun bind(dataItem: Comentario, position: Int){
            itemView.tvautor.text = dataItem.autor
            itemView.tvfechacom.text = dataItem.fechapubli.toString()
            itemView.tvcomentario.text = dataItem.textocomentario
        }

    }

}