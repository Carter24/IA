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
import kotlinx.android.synthetic.main.activity_productos.*
import kotlinx.android.synthetic.main.actualizar_producto.*

class AdapterProducto(val ctx : Context, val layoutId:Int, val listaProducto:List<ProductosConstructor>)
    : ArrayAdapter<ProductosConstructor>(ctx,layoutId,listaProducto){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(layoutId,null)

        val nombre = view.findViewById<TextView>(R.id.NombreProducto2)

        val actualizar = view.findViewById<TextView>(R.id.Actualizar2)
        val borrar = view.findViewById<TextView>(R.id.Eliminar2)
        val producto = listaProducto[position]

        nombre.text = producto.nombre

        actualizar.setOnClickListener {
            Actualizar(producto)
        }

        borrar.setOnClickListener {
            Borrar(producto)
        }

        return view
    }

    private  fun Actualizar(producto: ProductosConstructor){

        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Actualizar")
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.actualizar_producto,null)

        val nombre = view.findViewById<TextView>(R.id.upNombreProducto)

        nombre.setText(producto.nombre)

        builder.setView(view)

        builder.setPositiveButton("Actualizar",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                val  base = FirebaseDatabase.getInstance().getReference("Productos")

                val Nombre    = nombre.text.toString().trim()

                if (Nombre.isEmpty()){
                    nombre.error = "Ingrese el Nombre del Producto "
                    return
                }
                else
                {
                    val producto = ProductosConstructorActualizar(producto.id,Nombre)
                    base.child(producto.id).setValue(producto)
                    Toast.makeText(ctx,"Actualizado", Toast.LENGTH_LONG).show()
                }
            }})

        builder.setNegativeButton("Cancelar",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })
        val alert = builder.create()
        alert.show()
    }

    private fun Borrar(producto: ProductosConstructor){
        val Base = FirebaseDatabase.getInstance().getReference("Productos")
        Base.child(producto.id).removeValue()
        Toast.makeText(ctx,"Eliminado", Toast.LENGTH_LONG).show()
    }
}