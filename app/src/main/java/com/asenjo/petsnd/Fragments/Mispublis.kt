package com.asenjo.petsnd.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asenjo.petsnd.Adapters.AdapterFavoritas
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mispublis.*

class Mispublis : Fragment() {

    companion object {
        var TAG = "tagmispublis"
    }

    private lateinit var adapter: AdapterFavoritas
    private lateinit var refFav: DatabaseReference
    private lateinit var listaFav: ArrayList<Publicacion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.activity_mispublis, container, false)


        return rootView

    }

}
