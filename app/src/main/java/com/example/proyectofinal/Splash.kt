package com.example.proyectofinal

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.TestLooperManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class Splash : AppCompatActivity() {
    lateinit var textoSpash:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        textoSpash=findViewById(R.id.textoSplash)


        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

        val imagenCarga=findViewById<ImageView>(R.id.imagenSplashPortada)
        Glide.with(this).load(R.drawable.load).into(imagenCarga)

        var face= Typeface.createFromAsset(assets,"fonts/Florilane Cardillac.ttf")

        textoSpash.typeface=face
    }
}