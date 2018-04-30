package com.asenjo.petsnd.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asenjo.petsnd.Adapters.AdapterMisPublicaciones
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import com.asenjo.petsnd.Views.Detail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Mispublis : Fragment() {

    companion object {
        var TAG = "tagmispublis"
    }

    private lateinit var adapter: AdapterMisPublicaciones
    private lateinit var lista: ArrayList<Publicacion>
    private lateinit var myRv: RecyclerView
    private lateinit var refMisPub: DatabaseReference

    //coger el nombre de la autenticacion y recortar hasta el @
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private val currentUser = mAuth!!.currentUser
    private val nameUser = currentUser!!.email!!.toString().substringBefore('@', currentUser.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.activity_mispublis, container, false)

        lista = ArrayList()
        cargarDatosFirebase()

        myRv = rootView.findViewById(R.id.rvmispublis) as RecyclerView

        return rootView
    }

    private fun cargarDatosFirebase() {
        //obtener datos de firebase
        val database = FirebaseDatabase.getInstance()
        refMisPub = database.getReference("publicacionesNuevo")
        refMisPub.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(Detail.TAG, dataSnapshot.childrenCount.toString())
                lista.clear()
                for (dataSnapshothijo in dataSnapshot.children) {
                    val publi = dataSnapshothijo.getValue(Publicacion::class.java)
                    //comprobar que el uploader es el mismo que el usuario para mostrar sus publicaciones
                    if (publi!!.uploader.equals(nameUser)) {
                        //si se cumple la condicion, añadir la publicacion a la lista
                        lista.add(publi!!)
                    }
                }

                //hay que llamar al adaptador aqui porque firebase es asincrono
                cargarAdaptador()

            }

            override fun onCancelled(error: DatabaseError) {
                //mostrar error en el logcat si no se ha podido leer
                Log.e(TAG, "Error de lectura.", error.toException())
            }
        })
    }

    private fun cargarAdaptador() {
        myRv.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterMisPublicaciones(activity, R.layout.rowmipublicacion, lista)
        myRv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
