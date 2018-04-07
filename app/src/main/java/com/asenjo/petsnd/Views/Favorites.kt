package com.asenjo.petsnd.Views

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.asenjo.petsnd.Adapters.AdapterFavoritas
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import kotlinx.android.synthetic.main.activity_favorites.*

class Favorites : AppCompatActivity() {

    companion object {
        private val REQUEST_DETALLE=0
    }

    private lateinit var adapter: AdapterFavoritas
    private lateinit var listaFav: ArrayList<Publicacion>
    private var isFav = false
    private lateinit var shPublisFav: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        //inicializar el array
        listaFav = ArrayList()

        //coger los elementos del sharedpreferences
        shPublisFav = getSharedPreferences("favoritas", Context.MODE_PRIVATE)

        //configurar el adaptador del recycler view
        rvfavorites.layoutManager = LinearLayoutManager(this)
        adapter = AdapterFavoritas(this,R.layout.rowfavorite,listaFav)
        rvfavorites.adapter = adapter

        getFavorites()

    }

    private fun getFavorites() {
        val favoritas = shPublisFav.all
        listaFav.clear()
        for ((entry,value) in favoritas.entries){
            listaFav.add(Publicacion(entry.toString(),value.toString()))
        }
        adapter.notifyDataSetChanged()
    }
}
