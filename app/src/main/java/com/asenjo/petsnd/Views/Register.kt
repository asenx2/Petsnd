package com.asenjo.petsnd.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.asenjo.petsnd.R
import kotlinx.android.synthetic.main.activity_register.*

//Activity para registrarse por primera vez en la app. Con authentication con en el foro.
//Hablar con carlos para que me diga qué tuvo que hacer para el foro además de usar los metodos apropiados
class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //si pulso en el boton me manda al login de nuevo y se intenta registrar el usuario
        btnRegister.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this,"El usuario se ha registrado correctamente",Toast.LENGTH_LONG)
            val intent = Intent(this,Login::class.java)
            this.startActivity(intent)
        })
    }
}
