package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductListBizcochos : AppCompatActivity() {
    lateinit var lista: ListView
    lateinit var botonCarritoBizcocho: FloatingActionButton
    var listaProductos= arrayListOf<Product>()
    lateinit var textoVacio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list_bizcochos)

        botonCarritoBizcocho=findViewById(R.id.botonCarritoBizcocho)
        lista=findViewById(R.id.listViewProductsBizcochos)
        textoVacio=findViewById(R.id.textoVistaVaciaBizcocho)

        obtenerProductos()
        lista.setOnItemClickListener {parent,view,position,id->
            Datos.productosCarrito.add(listaProductos[position])
            val toast=
                Toast.makeText(applicationContext,"Producto añadido al carrito", Toast.LENGTH_LONG).show()
        }

        botonCarritoBizcocho.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            intent.putExtra("producto",Datos.productosCarrito)
            startActivity(intent)
        }
    }
    fun obtenerProductos(){

        val db= Firebase.firestore
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result ) {
                    val categoria=document.data["categoria"].toString()
                    val nombre=document.data["nombre"].toString()
                    val precio=document.data["precio"].toString().toFloat()
                    val currentProduct=Product(categoria,nombre,precio,document.id)
                    if(currentProduct.categoria=="bizcocho" || currentProduct.categoria=="Bizcocho" ) {
                        listaProductos.add(currentProduct)
                    }

                }
                var productArrayAdapter = ProductArrayAdapter(applicationContext, R.layout.activity_list_item, listaProductos)
                lista.adapter=productArrayAdapter
                if(lista.isEmpty()){
                    lista.emptyView=textoVacio
                }else{

                }

            }
            .addOnFailureListener { exception ->
                lista.emptyView=textoVacio
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
                val i = Intent(applicationContext, ProductListBizcochos::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}