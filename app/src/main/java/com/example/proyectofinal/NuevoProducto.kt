package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NuevoProducto : AppCompatActivity() {
    lateinit var botonAlta:Button
    lateinit var editCategoria:EditText
    lateinit var editNombre:EditText
    lateinit var editPrecio:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        botonAlta=findViewById(R.id.botonAltaProducto)
        editCategoria=findViewById(R.id.editCategoriaAddPro)
        editNombre=findViewById(R.id.editNombreAddPro)
        editPrecio=findViewById(R.id.editPrecioAddPro)



        botonAlta.setOnClickListener {
            nuevoProducto()
        }

    }


    fun nuevoProducto(){
        if(editCategoria.text.toString().isEmpty() || editNombre.text.toString().isEmpty() || editPrecio.text.toString().isEmpty()){
            val toast=
                Toast.makeText(applicationContext,"Algún campo está vacío.", Toast.LENGTH_LONG).show()
        }else{
            val db= Firebase.firestore
            val producto= hashMapOf(
                "categoria" to editCategoria.text.toString(),
                "nombre" to editNombre.text.toString(),
                "precio" to editPrecio.text.toString().toFloat()
            )
            db.collection("productos")
                .add(producto)
                .addOnSuccessListener { documentReference ->
                    val toast= Toast.makeText(applicationContext,"EL producto se ha creado con éxito con el id: ${documentReference.id}",
                        Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    alert()
                }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opcion1 -> {
                FirebaseAuth.getInstance().signOut()
                val i = Intent(applicationContext, LoginActivity::class.java)
                startActivity(i)
            }
        }
        when (item.itemId) {
            android.R.id.home -> {
                val i = Intent(applicationContext, ProductListAdmin::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}