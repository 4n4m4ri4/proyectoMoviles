package com.example.proyectofinal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity

class CompraActivity : AppCompatActivity() {
    lateinit var botonConfirma:Button
    lateinit var botonCancelar:Button
    lateinit var editNombre:EditText
    lateinit var imagenMarca:ImageView
    lateinit var textoCompraExito:TextView
    lateinit var checkTarjeta:CheckBox
    lateinit var checkEfectivo:CheckBox
    var tarjeta=""
    var efectivo=""
    var productos= arrayListOf<Product>()
    var nombres= arrayListOf<String>()
    var cantidadTotal=0
    var precioTotal=0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra)

        productos=intent.getSerializableExtra("prod")as ArrayList<Product>
        cantidadTotal=intent.getSerializableExtra("cantidadTotal")as Int
        precioTotal=intent.getSerializableExtra("precioTotal")as Float
        botonConfirma=findViewById(R.id.botonConfirmar)
        botonCancelar=findViewById(R.id.botonCancelar)
        editNombre=findViewById(R.id.editTextNombreUsu)
        imagenMarca=findViewById(R.id.imagenMarca)
        textoCompraExito=findViewById(R.id.textoExito)
        checkTarjeta=findViewById(R.id.checkTarjeta)
        checkEfectivo=findViewById(R.id.checkEfectivo)

        comprobacionesYBotones()

    }
    fun comprobacionesYBotones(){

            botonConfirma.setOnClickListener {
                if(editNombre.text.toString().isEmpty()){
                    val toast=Toast.makeText(applicationContext,"El nombre no puede estar vacío.",Toast.LENGTH_LONG).show()
                }else{
                    imagenMarca.visibility=View.VISIBLE
                    textoCompraExito.visibility=View.VISIBLE

                    hacerPedido()
                }

            }
    }
    fun hacerPedido(){
        tarjeta = if(checkTarjeta.isChecked){
            checkTarjeta.isChecked = true
            "Si"
        }else{
            checkTarjeta.isChecked = false
            "No"
        }
        efectivo = if(checkEfectivo.isChecked){
            checkEfectivo.isChecked=true
            "Si"
        }else{
            checkEfectivo.isChecked=false
            "No"
        }
        var addresses= arrayOf("anamarialogo98@gmail.com")
        var subject="Pedido de ${editNombre.text}"
        for (position in productos.indices)
            nombres.add(productos[position].nombre)
        var cuerpo= "Productos: \n$nombres\n"+ "Cantidad de productos : $cantidadTotal\n" + "Precio total: $precioTotal € \n"+
         "Pago con tarjeta: $tarjeta\n"+" Pago en efectivo: $efectivo"
        enviarEmail(addresses,subject,cuerpo)
    }
    fun enviarEmail(addresses:Array<String>,subject: String,cuerpo: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data= Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT,cuerpo)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}