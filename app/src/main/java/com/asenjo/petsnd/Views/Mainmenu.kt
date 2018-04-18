package com.asenjo.petsnd.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.asenjo.petsnd.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_mainmenu.*
import android.widget.Toast

class Mainmenu : AppCompatActivity() {

    private var backButtonCount: Int = 0
    private lateinit var mAuth: FirebaseAuth

    //activity con el menu para ir a las diferentes pantallas
    //hay que convertir esta activity en la inicial al abrir la app una vez que el usuario se haya registrado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

        mAuth = FirebaseAuth.getInstance()

        //con el boton nuevo muestro la pantalla para añadir una nueva publicacion
        btnNueva.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Nuevapubli::class.java)
            this.startActivity(intent)
        })

        //con el boton todas muestro la lista completa
        btnTodas.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Mainrv::class.java)
            this.startActivity(intent)
        })

        //con el boton favoritos muestro aquellas marcadas por el usuario
        //hasta que no esté hecho la parte del login no puedo implementar los favoritos
        btnFav.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Favorites::class.java)
            this.startActivity(intent)
        })

        //con el ultimo boton muestro la información del usuario registrado
        btnPerf.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Perfiltabs::class.java)
            this.startActivity(intent)
        })
    }

    //funcion para salir de la aplicacion cuando esté en el menú (lo que indica que ya me he registrado y no quiero ir al login)
    //tambien se establece como la activity de inicio al volver a abrir la app
    override fun onBackPressed() {
        if (backButtonCount >= 1) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            Toast.makeText(this, "Vuelve a pulsar para cerrar la aplicación", Toast.LENGTH_SHORT).show()
            backButtonCount++
        }
    }
}
