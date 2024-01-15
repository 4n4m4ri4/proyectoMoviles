package com.example.proyectofinal

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var textoBienvenida:TextView
    lateinit var botonAcceder:Button
    lateinit var botonRegistro:Button
    lateinit var editEmail:EditText
    lateinit var editPassword:EditText
    lateinit var mProgressBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textoBienvenida=findViewById(R.id.textoBienvenida)
        botonAcceder=findViewById(R.id.botonAcceso)
        botonRegistro=findViewById(R.id.botonRegistro)
        editEmail=findViewById(R.id.editTextEmail)
        editPassword=findViewById(R.id.editTextPassword)
        mProgressBar = ProgressDialog(this)


        var face= Typeface.createFromAsset(assets,"fonts/Florilane Cardillac.ttf")

        textoBienvenida.typeface=face

        onclicksBotones()

    }


    fun onclicksBotones(){
        botonRegistro.setOnClickListener {
            registro()
        }
        botonAcceder.setOnClickListener {
          acceso()
        }

    }
    fun registro(){
        if(editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
            mProgressBar.setMessage("Registering User...")
            mProgressBar.show()
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(editEmail.text.toString(),
                    editPassword.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        mProgressBar.hide()
                        val toast= Toast.makeText(applicationContext,"El usuario se ha registrado con éxito",
                            Toast.LENGTH_LONG).show()
                    }else{
                        mProgressBar.hide()
                        alert()
                    }
                }
        }else{
            val toast= Toast.makeText(applicationContext,"Alguno de los campos está vacío", Toast.LENGTH_LONG).show()
        }
    }
    fun acceso(){
        if(editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(editEmail.text.toString(),
                    editPassword.text.toString()).addOnCompleteListener{

                    if(it.isSuccessful && !editEmail.text.toString().equals("anita@gmail.com")){
                        val i=Intent(applicationContext,PrincipalActivity::class.java)
                        startActivity(i)
                    }else if(it.isSuccessful && editEmail.text.toString().equals("anita@gmail.com")){
                        val e=Intent(applicationContext,ProductListAdmin::class.java)
                        startActivity(e)
                    }
                    else{
                        alert()
                    }

                }
        }else{
            val toast=Toast.makeText(applicationContext,"Alguno de los campos está vacío",Toast.LENGTH_LONG).show()
        }

    }

    fun alert(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error.")
        builder.setPositiveButton("Ok",null)
        val dialog=builder.create()
        dialog.show()
    }

}