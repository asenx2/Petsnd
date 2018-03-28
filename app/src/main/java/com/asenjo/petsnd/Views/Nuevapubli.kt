package com.asenjo.petsnd.Views

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.asenjo.petsnd.Model.Publicacion
import com.asenjo.petsnd.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_nuevapubli.*
import java.io.IOException
import java.util.*

class Nuevapubli : AppCompatActivity() {

    //constant to track image chooser intent
    private val PICK_IMAGE_REQUEST = 234

    //uri para almacenar las fotos que subo
    private lateinit var filePath: Uri

    private lateinit var storageReference: StorageReference
    private lateinit var mDatabase: DatabaseReference

    //coger el nombre de la autenticacion y recortar hasta el @
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    val currentUser = mAuth!!.currentUser
    val nameUser = currentUser!!.email!!.toString().substringBefore('@', currentUser.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevapubli)

        supportActionBar!!.setTitle("Nueva publicación")

        storageReference = FirebaseStorage.getInstance().reference
        //decir a database en qué lista guardar la informacion que le paso
        mDatabase = FirebaseDatabase.getInstance().getReference("publicaciones")

        //boton para seleccionar el archivo que quiero subir
        btnSelect.setOnClickListener { view ->
            showFileChooser()
        }

        //boton para subir la imagen
        btnPublish.setOnClickListener{ view ->
            uploadFile()
        }

        //boton para ir a la lista de publicaciones
        btnBack.setOnClickListener { view ->
            val intent = Intent(this, Mainrv::class.java)
            startActivity(intent)
        }

    }

    //metodo que muestra la galeria para escoger una imagen
    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST)
    }

    //si he seleccionado una imagen y no está vacía, se muestra en el ivpreview de la activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                ivpreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getFileExtension(uri: Uri): String {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        //si la imagen está disponible...
        if (filePath != null) {
            //mostrar dialogo de progreso mientras se sube
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Subiendo...")
            progressDialog.show()

            //coger la referencia de storage para guardar la foto
            val sRef = storageReference!!.child("images/" + System.currentTimeMillis() + "." + getFileExtension(filePath))

            //añadir la imagen al storage
            sRef.putFile(filePath)
                    .addOnSuccessListener { taskSnapshot ->
                        //cuando se sube la imagen se detiene el dialogo de progreso
                        progressDialog.dismiss()

                        //mostrar tostada de éxito
                        Toast.makeText(applicationContext, "Imagen subida", Toast.LENGTH_LONG).show()

                        //crear el objeto publicacion con los datos de los edit text y la url de la imagen subida
                        val upload = Publicacion(nameUser,etTituloUp.text.toString().trim { it <= ' ' },etDescUp.text.toString() ,Date(),taskSnapshot.downloadUrl!!.toString())

                        //añadir la publicación a la base de datos
                        val uploadId = mDatabase!!.push().key
                        mDatabase!!.child(uploadId).setValue(upload)

                        //al realizar la subida, volver a la vista mainrv
                        val intent = Intent(this, Mainrv::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { exception ->
                        //si se produce un fallo se muestra la tostada con el error y se detiene el dialogo de progreso
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        //mostrar el dialogo de progreso
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Subido " + progress.toInt() + "%...")
                    }
        } else {
            //si no se ha seleccionado ninguna imagen, no se muestra
            Toast.makeText(applicationContext, "No se ha seleccionado ninguna imagen", Toast.LENGTH_LONG).show()
        }
    }
}
