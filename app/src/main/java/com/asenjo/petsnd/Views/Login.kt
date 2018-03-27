package com.asenjo.petsnd.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asenjo.petsnd.R
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    //activity para logearse mediante authentication firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //boton para ir a la activity para registrarse
        tvRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Register::class.java)
            this.startActivity(intent)
        })

        //si pulso en el boton y ya existe el usuario me manda al menu
        btnEnter.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Mainmenu::class.java)
            this.startActivity(intent)
        })
    }
}
