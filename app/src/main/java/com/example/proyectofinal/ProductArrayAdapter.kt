package com.example.proyectofinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ProductArrayAdapter (context: Context, viewToPaint: Int, private val productList: ArrayList<Product>)
    : ArrayAdapter<Product>(context,viewToPaint,productList){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater= LayoutInflater.from(context)
        val currentListItem= inflater.inflate(R.layout.activity_list_item,null)

        val imagen=currentListItem.findViewById<ImageView>(R.id.imagenItem)
        val textName=currentListItem.findViewById<TextView>(R.id.textName)
        val textPrice=currentListItem.findViewById<TextView>(R.id.textPrice)
        val textCategory=currentListItem.findViewById<TextView>(R.id.textCategory)
        val imagenAdd=currentListItem.findViewById<ImageView>(R.id.aniadirAlCarrito)

        textCategory.text=productList.get(position).categoria
        textName.text=productList.get(position).nombre
        textPrice.text=productList.get(position).precio.toString()

        if(textCategory.text.equals("cupcake") || textCategory.text.equals("Cupcake")){
            imagen.setImageResource(R.drawable.cupcakeitem)
        }
        if(textCategory.text.equals("tarta") || textCategory.text.equals("Tarta")){
            imagen.setImageResource(R.drawable.tarta)
        }
        if(textCategory.text.equals("galleta") || textCategory.text.equals("Galleta")){
            imagen.setImageResource(R.drawable.galleta)
        }
        if(textCategory.text.equals("bizcocho") || textCategory.text.equals("Bizcocho")){
            imagen.setImageResource(R.drawable.bizco)
        }

        imagenAdd.setImageResource(R.drawable.add)

        return currentListItem
    }
}


