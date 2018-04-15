package com.asenjo.petsnd.Adapters

import android.app.Activity
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
import kotlinx.android.synthetic.main.rowfavorite.view.*
import kotlinx.android.synthetic.main.rowmipublicacion.view.*
import kotlinx.android.synthetic.main.rowpub.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by asenjo on 26/03/2018.
 */
class AdapterMisPublicaciones(val context: Context,
                              val layout: Int,
                              val dataList: ArrayList<Publicacion>
) : RecyclerView.Adapter<AdapterMisPublicaciones.ViewHolder>() {

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
            Picasso.with(context).load(url).into(itemView.ivrowmipub)

            itemView.tvmifecha.text = SimpleDateFormat("dd/MM/yyyy").format(dataItem.fechaupload)
            itemView.tvrowmititulo.text = dataItem.titulo

        }

    }

}