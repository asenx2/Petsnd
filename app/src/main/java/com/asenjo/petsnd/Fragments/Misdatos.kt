package com.asenjo.petsnd.Fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.asenjo.petsnd.R
import com.asenjo.petsnd.Views.Login
import com.google.firebase.auth.FirebaseAuth


class Misdatos : Fragment() {

    companion object {
        var TAG = "***tagmisdatos***"
    }

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

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
        val tvmimail = rootView.findViewById(R.id.tvmimail) as TextView
        val tvmialta = rootView.findViewById(R.id.tvmialta) as TextView

        tvmimail.text = mAuth!!.currentUser!!.email!!.toString()
        tvmialta.text = "ace musho"

        btnout.setOnClickListener(View.OnClickListener {
            //signout()

            //para obtener el contexto funciona activity y context en lugar de this
            Toast.makeText(activity,"Ande bas?!",Toast.LENGTH_SHORT).show()
        })

        return rootView
    }



    //funcion para cerrar sesion y volver a la pantalla inicial
    private fun signout() {
        mAuth!!.signOut()
        val intent = Intent(context,Login::class.java)
        startActivity(intent)
    }

}
