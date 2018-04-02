package com.asenjo.petsnd.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.asenjo.petsnd.Model.Usuario
import com.asenjo.petsnd.R
import com.asenjo.petsnd.Views.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Misdatos : Fragment() {

    companion object {
        var TAG = "***tagmisdatos***"
    }

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var usuarioRegistrado: Usuario
    private val currentUser = mAuth!!.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.activity_misdatos, container, false)

        //para coger el boton dentro de un fragmento hay que hacerlo mediante findViewById
        //si lo hago mediante las extension de synthetic la activity no se abre
        val btnout = rootView.findViewById(R.id.btnout) as Button
        btnout.setOnClickListener(View.OnClickListener {
            signout()
        })

        val mail = rootView.findViewById(R.id.tvmail) as TextView
        mail.text = currentUser!!.email

        return rootView
    }

    //funcion para cerrar sesion y volver a la pantalla inicial
    private fun signout() {
        mAuth!!.signOut()
        val intent = Intent(context,Login::class.java)
        startActivity(intent)
    }

}
