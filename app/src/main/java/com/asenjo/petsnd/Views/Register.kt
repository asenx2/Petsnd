package com.asenjo.petsnd.Views

import android.os.Bundle
import android.view.View
import com.asenjo.petsnd.R
import kotlinx.android.synthetic.main.activity_register.*

//Activity para registrarse por primera vez en la app. Auth con email y contraseña
class Register : AuthFunctions() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //instancia del auth
//        mAuth = FirebaseAuth.getInstance()

        //si pulso en el boton me manda al login de nuevo y se intenta registrar el usuario
        btnRegister.setOnClickListener(View.OnClickListener {
            createAccount(etMailReg.text.toString(),etPassReg.text.toString())
        })
    }

}
