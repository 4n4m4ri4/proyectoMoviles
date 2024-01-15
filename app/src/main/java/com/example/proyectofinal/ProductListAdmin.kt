package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isEmpty
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductListAdmin : AppCompatActivity() {
    lateinit var lista:ListView
    lateinit var textoVacioAdmin:TextView
    lateinit var botonNuevo:FloatingActionButton
    var listaProductos= arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list_admin)

        lista=findViewById(R.id.listViewProductsAdmin)
        botonNuevo=findViewById(R.id.botonNuevo)
        textoVacioAdmin=findViewById(R.id.textoVistaVaciaAdmin)


        botonNuevo.setOnClickListener {
            val i=Intent(applicationContext,NuevoProducto::class.java)
            startActivity(i)
        }

        getProducts()
        getProduct()
    }
    fun getProducts(){
        val db= Firebase.firestore
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result ) {
                    val categoria=document.data["categoria"].toString()
                    val nombre=document.data["nombre"].toString()
                    val precio=document.data["precio"].toString().toFloat()
                    val currentProduct=Product(categoria,nombre,precio,document.id)
                    listaProductos.add(currentProduct)

                }
                var productArrayAdapter = ProductArrayAdapter(applicationContext, R.layout.activity_list_item, listaProductos)
                lista.adapter = productArrayAdapter
                if(lista.isEmpty()){
                    lista.emptyView=textoVacioAdmin
                }else{

                }

            }
            .addOnFailureListener { exception ->
                lista.emptyView=textoVacioAdmin
            }
    }
    fun getProduct() {
        lista.setOnItemClickListener {parent,view,position,id->
            val intent = Intent(this, EditAndModifyActivity::class.java)
            intent.putExtra("productEdit",listaProductos[position])
            startActivity(intent)
        }

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
                val i = Intent(applicationContext, LoginActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}