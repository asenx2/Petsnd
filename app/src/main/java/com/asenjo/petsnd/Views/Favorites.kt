package com.asenjo.petsnd.Views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.asenjo.petsnd.Adapters.AdapterFavoritas
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_favorites.*

class Favorites : AppCompatActivity() {

    private lateinit var adapter: AdapterFavoritas
    private lateinit var listaFav: ArrayList<Publicacion>
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

        //mostrar tostada si la lista de favoritas está vacía
        if(listaFav.size == 0){
            Toast.makeText(this,"No tienes ninguna publicación favorita", Toast.LENGTH_LONG).show()
        }

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


    fun cargarAdaptador() {
        //configurar el adaptador del recycler view
        rvfavorites.layoutManager = LinearLayoutManager(this)
        adapter = AdapterFavoritas(this,R.layout.rowfavorite,listaFav)
        rvfavorites.adapter = adapter
        refrescar()
    }

    private fun refrescar(){
        adapter.notifyDataSetChanged()
    }

    //metodo para que al volver desde la vista detalle y eliminar de favorito
    //se refresque la pagina y no se muestre en la lista de favoritos la eliminada
    public override fun onRestart() {
        super.onRestart()
        finish()
        this.startActivity(intent)
    }

}
