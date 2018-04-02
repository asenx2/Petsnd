package com.asenjo.petsnd.Views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.asenjo.petsnd.Adapters.AdapterComentarios
import com.asenjo.petsnd.Model.Comentario
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_detail.*
import org.jetbrains.anko.*
import java.util.*

class Detail : AppCompatActivity() {

    companion object {
        val TAG = "***tagdetail***"
    }

    private lateinit var listacom: ArrayList<Comentario>
    private lateinit var adapter: AdapterComentarios
    private lateinit var refCom: DatabaseReference
    private lateinit var pubclick: Publicacion

    //coger el nombre de la autenticacion y recortar hasta el @
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    val currentUser = mAuth!!.currentUser
    val nameUser = currentUser!!.email!!.toString().substringBefore('@', currentUser.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //setSupportActionBar(toolbar)

        //comento la linea superior para no mostrar la apptoolbar
        //lo guardo por si lo utilizo en el futuro

        //recoger la publicacion que he pulsado en la activity anterior
        pubclick = intent.getSerializableExtra("publipulsada") as Publicacion

        //fijar los textview con la informacion de la publicacion que he recogido
        tvuploaderdetail.text = pubclick.uploader
        tvtitulodetail.text = pubclick.titulo

        //fijar imagen de la vista detalle
        val url = pubclick.urlimage
        Picasso.with(this).load(url).into(ivdetail)

        //inicializar la lista de comentarios de la publicacion pulsada
        listacom = ArrayList()

        //configurar el adaptador para el recycler view de comentarios
        rvcomen.layoutManager = LinearLayoutManager(this)
        adapter = AdapterComentarios(this,R.layout.rowcom,listacom)
        rvcomen.adapter = adapter

        //obtener datos de firebase
        val database = FirebaseDatabase.getInstance()
        refCom = database.getReference("comentarios")
        refCom.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e(TAG, dataSnapshot.childrenCount.toString())
                listacom.clear()
                for (dataSnapshothijo in dataSnapshot.children) {
                    val coment = dataSnapshothijo.getValue(Comentario::class.java)
                    //cojo el titulo de la publicacion y la del comentario (que se le pasa para que sea el mismo) para
                    //que solo se muestren los comentarios de una publicacion concreta
                    //hago lo mismo con la fecha por si dos publicaciones tuvieran el mismo nombre
                    if (pubclick.titulo.equals(coment!!.titulopadre) && pubclick.fechaupload.equals(coment!!.fechapadre)) {
                        //si se cumple la condicion, añadir el comentario al arraylist
                        listacom.add(coment!!)
                    }
                }
                //invertir la lista para que se muestren los ultimos en la parte superior
                listacom.reverse()
                //notificar los cambios al recycler view para mostrar el nuevo comentario mostrado
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //mostrar error en el logcat si no se ha podido leer
                Log.e(TAG, "Error de lectura.", error.toException())
            }
        })

        //imagebutton dentro de la vista detail para mostrar la imagen en pantalla completa
        //paso la url de la imagen a la vista imagefull
        ivdetail.setOnClickListener { view ->
            val intent = Intent(this,ImageFull::class.java)
            intent.putExtra("image",pubclick.urlimage)
            this.startActivity(intent)
        }

        //boton para mostrar el dialogo anko para añadir un nuevo comentario dentro de la publicacion
        btnComentar.setOnClickListener { view ->
            dialogComent()
        }
    }

    //dialogo para insertar un nuevo comentario
    private fun dialogComent() {
        alert {
            customView {
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent)
                    val etComent = editText {
                        //hint = "Comentario"
                        setHighlightColor(Color.BLACK)
                        setTextSize(22f)
                    }

                    negativeButton("Enviar") {
                        if (etComent.text.length == 0)
                            //etComent.text.isBlank()
                            //añadir al if para que no se pueda insertar un comentario con espacios
                            longToast("El comentario no puede estar vacío")
                        else
                            //crear nuevo comentario con el usuario registrado y los demás valores recogidos
                            refCom.push().setValue(Comentario(Date(),nameUser,pubclick.titulo,pubclick.fechaupload,etComent.text.toString()))
                    }
                    positiveButton("Cerrar") {}
                }
            }
        }.show()
    }

}
