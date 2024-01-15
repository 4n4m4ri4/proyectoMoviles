package com.example.proyectofinal

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PrincipalActivity : AppCompatActivity() {
    lateinit var categoriaCupcake:RelativeLayout
    lateinit var categoriaTarta:RelativeLayout
    lateinit var categoriaGalleta:RelativeLayout
    lateinit var categoriaBizcocho:RelativeLayout
    lateinit var botonMapa:Button
    lateinit var imagenTlf:ImageView
    lateinit var imagenFacebook:ImageView
    lateinit var imagenGoogle:ImageView
    lateinit var imagenInsta:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        categoriaCupcake=findViewById(R.id.relativeCategoriaCupcake)
        botonMapa=findViewById(R.id.botonMapa)
        categoriaTarta=findViewById(R.id.relativeCategoriaTarta)
        categoriaGalleta=findViewById(R.id.relativeCategoriaGalleta)
        categoriaBizcocho=findViewById(R.id.relativeCategoriaBizcocho)
        imagenTlf=findViewById(R.id.imagenPhone)
        imagenFacebook=findViewById(R.id.imagenFacebook)
        imagenGoogle=findViewById(R.id.imagenGoogle)
        imagenInsta=findViewById(R.id.imagenInstagram)


        onclicksBotones()

    }
    fun onclicksBotones(){
        botonMapa.setOnClickListener {
            val i= Intent(applicationContext,MapaActivity::class.java)
            startActivity(i)
        }
        categoriaCupcake.setOnClickListener {
            val i= Intent(applicationContext,ProductListCupcakes::class.java)
            startActivity(i)
        }
        categoriaTarta.setOnClickListener {
            val i= Intent(applicationContext,ProductListTartas::class.java)
            startActivity(i)
        }
        categoriaGalleta.setOnClickListener {
            val i= Intent(applicationContext,ProductListGalletas::class.java)
            startActivity(i)
        }
        categoriaBizcocho.setOnClickListener {
            val i= Intent(applicationContext,ProductListBizcochos::class.java)
            startActivity(i)
        }
        imagenTlf.setOnClickListener {
            hacerLlamada()
        }
        imagenFacebook.setOnClickListener {
            openWeb("https://www.facebook.com/CentroNelson")
        }
        imagenInsta.setOnClickListener {
            openWeb("https://www.instagram.com/centronelson/")
        }
        imagenGoogle.setOnClickListener {
            openWeb("https://www.centronelson.org/")
        }

    }
    fun hacerLlamada() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:+34603396400")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    fun openWeb(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}