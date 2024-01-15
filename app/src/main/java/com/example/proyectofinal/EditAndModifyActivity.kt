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

class EditAndModifyActivity : AppCompatActivity() {
    lateinit var botonEliminar:Button
    lateinit var botonModificar:Button
    lateinit var editNombre:EditText
    lateinit var editCategoria:EditText
    lateinit var editPrecio:EditText
    lateinit var producto:Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_and_modify)

        producto=intent.getSerializableExtra("productEdit")as Product
        botonEliminar=findViewById(R.id.botonEliminarProducto)
        botonModificar=findViewById(R.id.botonModificarProducto)
        editNombre=findViewById(R.id.editNombreEditPro)
        editCategoria=findViewById(R.id.editCategoriaEditPro)
        editPrecio=findViewById(R.id.editPrecioEditPro)

        datosProducto()
        onclicksBotones()
    }


    fun onclicksBotones(){
        botonModificar.setOnClickListener {
            editarProducto()
        }
        botonEliminar.setOnClickListener {
            borrarProducto()
            val i= Intent(applicationContext,ProductListAdmin::class.java)
            startActivity(i)
        }

    }
    fun datosProducto() {
        editNombre.setText(producto.nombre)
        editCategoria.setText(producto.categoria)
        editPrecio.setText(producto.precio.toString())

    }
    fun editarProducto(){
        if (editNombre.text.toString().isEmpty() || editCategoria.text.toString().isEmpty() || editPrecio.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Alguno de los campos está vacio", Toast.LENGTH_LONG).show()
        }else{
            val db = Firebase.firestore
            db.collection("productos").document(producto.id)
                .update(
                    mapOf(
                        "nombre" to editNombre.text.toString(),
                        "categoria" to editCategoria.text.toString(),
                        "precio" to editPrecio.text.toString().toFloat()
                    )
                )
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(applicationContext, "El producto ha sido modificado con éxito.", Toast.LENGTH_LONG).show()

                }
                .addOnFailureListener { e ->
                    alert()
                }
        }
    }
    fun borrarProducto(){
        val db = Firebase.firestore
        db.collection("productos").document(producto.id)
            .delete()
            .addOnSuccessListener { Toast.makeText(applicationContext, "EL producto ha sido eliminado.", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener {
                alert()

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