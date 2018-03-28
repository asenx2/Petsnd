package com.asenjo.petsnd.Views

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.asenjo.petsnd.R
import kotlinx.android.synthetic.main.activity_login.*

class Login : AuthFunctions() {

    //activity para logearse mediante authentication firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //comprobar si hay un usuario registrado para abrir la app en el menu
        val currentUser = mAuth!!.currentUser
        checkUser(currentUser)

        //boton para ir a la activity para registrarse
        tvRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Register::class.java)
            this.startActivity(intent)
        })

        //si pulso en el boton y ya existe el usuario me manda al menu
        btnEnter.setOnClickListener(View.OnClickListener {
            signIn(etMailLog.text.toString(), etPassLog.text.toString())
        })
    }
}
