package com.asenjo.petsnd.Views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.asenjo.petsnd.Adapters.AdapterFavoritas
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_favorites.*

class Favorites : AppCompatActivity() {

    private lateinit var adapter: AdapterFavoritas
    private lateinit var listaFav: ArrayList<Publicacion>
    private var isFav = false
    private lateinit var shPublisFav: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        //coger los elementos del sharedpreferences
        shPublisFav = getSharedPreferences("favoritas", Context.MODE_PRIVATE)

        //inicializar el array de publicaciones
        listaFav = ArrayList()

        cargarFavoritasShared()
        cargarAdaptador()

    }

    private fun cargarFavoritasShared() {
        //obtener el xml con la lista de favoritos
        val favShared = shPublisFav.all
        //para cada entrada del xml de shared preferences...
        for(entry in favShared.entries){
            //coger la entrada y convertir a string para asegurarme
            val jsonPubli = entry.value.toString()
            //crear una publicacion con el json
            val favorita = Gson().fromJson(jsonPubli, Publicacion::class.java)
            //añadir a la lista de favoritos
            listaFav.add(favorita)
        }
    }


    private fun cargarAdaptador() {
        //configurar el adaptador del recycler view
        rvfavorites.layoutManager = LinearLayoutManager(this)
        adapter = AdapterFavoritas(this,R.layout.rowfavorite,listaFav)
        rvfavorites.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
