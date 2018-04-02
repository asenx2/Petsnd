package com.asenjo.petsnd.Model

import java.io.Serializable
import java.util.*

/**
 * Created by Asenjo on 27/03/2018.
 */
//Clase usuario con los datos para el registro y login. Hay que añadir más parámetros según lo vaya necesitando
data class Usuario(var email:String="",
                   var nick:String="",
                   var avatar:String="",
                   var fechaalta: Date = Date())

//Clase publicacion. urlimage debe coger el valor url del archivo subido a storage
//Clase serializada para pasar el objeto entero en el intent
data class Publicacion(var uploader:String="",
                       var titulo:String="",
                       var descripcion:String="",
                       var fechaupload: Date = Date(),
                       var urlimage:String="") : Serializable

//Clase comentario. Tiene valores padre de la publicacion a la que corresponden para hacer distincion entre comentarios
//No puede haber una publicacion con el mismo titulo y la misma fecha exacta
data class Comentario(var fechapubli: Date = Date(),
                      var autor:String="",
                      var titulopadre:String="",
                      var fechapadre:Date = Date(),
                      var textocomentario:String="")