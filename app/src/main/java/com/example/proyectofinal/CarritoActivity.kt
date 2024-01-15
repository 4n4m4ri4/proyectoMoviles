package com.example.proyectofinal


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isEmpty
import com.google.firebase.auth.FirebaseAuth



class CarritoActivity : AppCompatActivity() {
    lateinit var listaCarrito:ListView
    var productos= arrayListOf<Product>()
    lateinit var botonCompra:Button
    lateinit var totalProductos:TextView
    lateinit var totalPrecioProductos:TextView
    lateinit var textoVacio:TextView
    var precioTotal=0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        productos=intent.getSerializableExtra("producto")as ArrayList<Product>
        listaCarrito=findViewById(R.id.listaCarrito)
        botonCompra=findViewById(R.id.botonCompra)
        totalProductos=findViewById(R.id.cantidadProductos)
        totalPrecioProductos=findViewById(R.id.precioTotal)
        textoVacio=findViewById(R.id.textoVacioCarrito)




        var productArrayAdapter = ProductArrayAdapter(applicationContext, R.layout.activity_list_item, productos)
        listaCarrito.adapter = productArrayAdapter

        calcularTotal()
        botonCompra.setOnClickListener {
            val i = Intent(applicationContext, CompraActivity::class.java)
            i.putExtra("cantidadTotal",Datos.cantidadTotal)
            i.putExtra("precioTotal",precioTotal)
            i.putExtra("prod", productos)
            startActivity(i)
        }


    }
    fun calcularTotal(){

        if(productos.isEmpty()){
            totalProductos.text="Total productos: 0"
            totalPrecioProductos.text="Precio total: 0 €"

        }else{
            Datos.cantidadTotal=productos.size
            totalProductos.text="Total productos: " + Datos.cantidadTotal.toString()
            for(position in productos.indices)
                precioTotal=precioTotal + productos[position].precio
            totalPrecioProductos.text="Precio total: " + precioTotal.toString() + " €"
            botonCompra.isEnabled=true

        }
        if(listaCarrito.isEmpty()){
            listaCarrito.emptyView=textoVacio

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
                val i = Intent(applicationContext, PrincipalActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    }

