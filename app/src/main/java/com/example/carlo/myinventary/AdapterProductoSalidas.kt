package com.example.carlo.myinventary

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class AdapterProductoSalidas(val ctx : Context, val layoutId:Int, val listaProducto:List<ProductosSalidasConstructor>)
    : ArrayAdapter<ProductosSalidasConstructor>(ctx,layoutId,listaProducto){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(layoutId,null)

        val nombre = view.findViewById<TextView>(R.id.NombreProductoSalida2)
        val idInventario = view.findViewById<TextView>(R.id.IdInventarioSalida)
        val fechaSalida = view.findViewById<TextView>(R.id.FechaSalida2)
        val cantidad = view.findViewById<TextView>(R.id.CantidadSalida2)
        val producto = listaProducto[position]

        idInventario.text = producto.idInventario
        nombre.text = producto.nombre
        fechaSalida.text = producto.fechaEntrada
        cantidad.text = producto.cantidad

        return view
    }
}