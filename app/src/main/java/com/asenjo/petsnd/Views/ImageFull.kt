package com.asenjo.petsnd.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.asenjo.petsnd.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_full.*

class ImageFull : AppCompatActivity() {

    private lateinit var image:String

    //activity para mostrar la imagen del detail en tamaño completo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full)

        //cojo la url que me pasa la activity detail
        image = intent.getSerializableExtra("image") as String
        Picasso.with(this).load(image).into(ivfull)
    }
}
