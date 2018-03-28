package com.asenjo.petsnd.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.asenjo.petsnd.R
import com.asenjo.petsnd.Views.Login
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_misdatos.*

class Misdatos : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        //cuando cojo el boton de la view no me abre la vista con las tabs

        //btnout = activity.findViewById(R.id.btnout) as Button

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.activity_misdatos, container, false)

//        var btnout = activity.findViewById(R.id.btnout) as Button
//
//        btnout.setOnClickListener(View.OnClickListener {
//            signout()
//        })

        return rootView
    }

    private fun signout() {
        mAuth!!.signOut()
        val intent = Intent(context,Login::class.java)
        startActivity(intent)
    }

}
