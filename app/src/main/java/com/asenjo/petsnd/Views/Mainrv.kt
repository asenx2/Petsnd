package com.asenjo.petsnd.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import com.asenjo.petsnd.Adapters.AdapterPublis
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mainrv.*
import kotlinx.android.synthetic.main.content_mainrv.*

//vista principal con las diferentes publicaciones
//la toolbar tiene el boton de search para buscar las publicaciones de un usuario concreto
class Mainrv : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        val TAG = "***tagmain***"
    }

    private lateinit var adapter: AdapterPublis
    private lateinit var refPub: DatabaseReference
    private lateinit var listaPub: ArrayList<Publicacion>

    // ************* SearchView ************
    private lateinit var searchView: SearchView
    private lateinit var original: ArrayList<Publicacion>
    // ************* SearchView ************

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainrv)
        setSupportActionBar(toolbar)

        //inicializar arraylist con las publicaciones
        listaPub = ArrayList()

        // ************* SearchView ************
        //inicializar el arraylist de publicaciones que se usa para filtrar
        original = ArrayList<Publicacion>()
        // ************* / SearchView ************

        //configurar el adaptador del recycler view
        rvpublis.layoutManager = LinearLayoutManager(this)
        adapter = AdapterPublis(this, R.layout.rowpub,listaPub)
        rvpublis.adapter = adapter

        //obtener datos de firebase (realtime database)
        val database = FirebaseDatabase.getInstance()
        refPub = database.getReference("publicaciones")
        refPub.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(TAG, dataSnapshot.childrenCount.toString())
                listaPub.clear()
                for (dataSnapshothijo in dataSnapshot.children) {
                    val pub = dataSnapshothijo.getValue(Publicacion::class.java)
                    listaPub.add(pub!!)
                }
                listaPub.reverse()
                refresh()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e(TAG, "Error de lectura.", error.toException())
        }
        })

        //pulsar boton flotante para ir a la activity de nueva publicacion
        //tambien llego allí desde el menú inicial
        fab.setOnClickListener { view ->
            val intent = Intent(this,Nuevapubli::class.java)
            this.startActivity(intent)
        }
    }

    private fun refresh(){
        // ************* SearchView ************
        //borrar la lista del filtro y añadir los datos contenidos en el arraylist principal
        original.clear()
        original.addAll(listaPub)
        // ************* / SearchView ************
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* SearchView ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* / SearchView ************
        return true
    }

    // ************* SearchView ************
    override fun onQueryTextChange(query: String): Boolean {
        val filteredList = ArrayList<Publicacion>()

        //antes de filtrar cojo todos los datos
        listaPub.clear()
        listaPub.addAll(original)

        //realizar el filtro
        if (query.isEmpty()) {
            filteredList.addAll(listaPub)
        } else {
            listaPub.filterTo(filteredList) { it.uploader.startsWith(query, true) }
        }

        //colocar los datos filtrados en el arraylist principal y avisar al adaptador de los cambios
        listaPub.clear()
        listaPub.addAll(filteredList)
        adapter.notifyDataSetChanged()

        return false
    }

    //metodo necesario al implementar SearchView.OnQueryTextListener
    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }
    // ************* / SearchView ************
}
