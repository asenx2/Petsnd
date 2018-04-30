package com.asenjo.petsnd.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.asenjo.petsnd.Views.Info
import com.asenjo.petsnd.R


class Misdatos : Fragment() {

    companion object {
        var TAG = "***tagmisdatos***"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.activity_misdatos, container, false)

        val tvInfo = rootView.findViewById(R.id.tvInfo) as TextView

        tvInfo.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Info::class.java)
            this.startActivity(intent)
        })

        return rootView
    }

}
