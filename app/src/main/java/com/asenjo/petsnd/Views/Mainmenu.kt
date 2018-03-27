package com.asenjo.petsnd.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.asenjo.petsnd.R
import kotlinx.android.synthetic.main.activity_mainmenu.*

class Mainmenu : AppCompatActivity() {

    //activity con el menu para ir a las diferentes pantallas
    //hay que convertir esta activity en la inicial al abrir la app una vez que el usuario se haya registrado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

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
            val intent = Intent(this,Mainrv::class.java)
            this.startActivity(intent)
        })

        //con el ultimo boton muestro el perfil de usuario
        //ésta activity tendrá tabs para mostrar diferente informacion:
        //-mis datos personales
        //-mis publicaciones, donde podré borrarlas. así el uploader es el unico que puede borrar sus publicaciones mediante longclick
        //-etc...

//        btnPerf.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this,Perfil::class.java)
//            this.startActivity(intent)
//        })
    }
}
