package com.asenjo.petsnd.Views

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by Asenjo on 28/03/2018.
 */
//clase que van a heredar Register y Login para no sobrecargar de métodos ambas clases
//aqui estan los metodos para crear una cuenta, acceder con una cuenta ya creada y cerrar sesion
open class AuthFunctions : DialogClass() {
    companion object {
        var TAG = "***tagreglog***"
    }

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var refUsers: DatabaseReference

    //funcion para crear nueva cuenta. FUNCIONA CORRECTAMENTE
    fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateFormReg()) {
            return
        }
        //cuando empieza el proceso de creación, aparece el dialogo de progreso
        showProgressDialog()

        //método para crear un usuario con email y contraseña
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Si me he registrado vuelvo al login y muestro toast
                        Log.d(TAG, "createUserWithEmail:success")

                        //si no cierro la sesion siempre que lo abro se muestra el login en primer lugar
                        signout()
                        //mostrar toast si se ha registrado y volver al login para introducir los datos
                        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,Login::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "No se ha podido crear el usuario", Toast.LENGTH_SHORT).show()
                    }
                    //al terminar ocultar el dialogo de progreso
                    hideProgressDialog()
                }
    }

    //funcion para ingresar con una cuenta ya creada
    fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateFormLog()) {
            return
        }

        //cuando empieza el proceso de comprobacion, aparece el dialogo de progreso
        showProgressDialog()

        // metodo para acceder
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        //si se logea correctamente se guarda el usuario para acceder a la vista menu
                        val user = mAuth!!.currentUser
                        checkUser(user)

                    } else {
                        // si no se puede acceder, mostrar mensaje de error
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "No se ha podido acceder", Toast.LENGTH_SHORT).show()
                    }

                    //al terminar ocultar el dialogo de progreso
                    hideProgressDialog()
                }
    }

    //funcion para acceder a la app si el usuario no es nulo
    fun checkUser(user: FirebaseUser?){
        hideProgressDialog()
        if(user != null){
            goinapp()
        }
    }

    //funcion para acceder al menu principal de la app
    private fun goinapp() {
        val intent = Intent(this,Mainmenu::class.java)
        startActivity(intent)
    }

    //funcion publica para salir de la sesion y hacer un check nulo para que al abrir muestre el login de nuevo
    //esta función se usará en la parte del perfil del usuario
    fun signout(){
        mAuth!!.signOut()
        checkUser(null)
    }

    //funcion para comprobar que los campos de registro están completos y son válidos
    private fun validateFormReg(): Boolean {
        var valid = true

        //comprobar el email
        val email = etMailReg.text.toString()
        if (TextUtils.isEmpty(email)) {
            etMailReg.error = "Campo obligatorio"
            valid = false
        } else {
            etMailReg.error = null
        }

        //comprobar la contraseña
        val password = etPassReg.text.toString()
        if (TextUtils.isEmpty(password)) {
            etPassReg.error = "Campo obligatorio"
            valid = false
        } else {
            etPassReg.error = null
        }

        return valid
    }

    //funcion para comprobar que los campos de inicio de sesión están completos y son válidos
    private fun validateFormLog(): Boolean {
        var valid = true

        //comprobar el email
        val email = etMailLog.text.toString()
        if (TextUtils.isEmpty(email)) {
            etMailLog.error = "Campo obligatorio"
            valid = false
        } else {
            etMailLog.error = null
        }

        //comprobar la contraseña
        val password = etPassLog.text.toString()
        if (TextUtils.isEmpty(password)) {
            etPassLog.error = "Campo obligatorio"
            valid = false
        } else {
            etPassLog.error = null
        }

        return valid
    }
}