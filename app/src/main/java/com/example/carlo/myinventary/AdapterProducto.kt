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

class AdapterProducto(val ctx : Context, val layoutId:Int, val listaProducto:List<ProductosConstructor>)
    : ArrayAdapter<ProductosConstructor>(ctx,layoutId,listaProducto){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(layoutId,null)

        val nombre = view.findViewById<TextView>(R.id.NombreProducto2)
        val idInventario = view.findViewById<TextView>(R.id.IdInventario)
        val fechaEntrada = view.findViewById<TextView>(R.id.Fecha)
        val cantidad = view.findViewById<TextView>(R.id.cantidad)

        val actualizar = view.findViewById<TextView>(R.id.Actualizar2)
        val borrar = view.findViewById<TextView>(R.id.Eliminar2)
        val producto = listaProducto[position]

        idInventario.text = producto.idInventario
        nombre.text = producto.nombre
        fechaEntrada.text = producto.fechaEntrada
        cantidad.text = producto.cantidad

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
        val fechaEntrada = view.findViewById<TextView>(R.id.upFechaEntrada)
        val cantidad  = view.findViewById<TextView>(R.id.upCantidad)

        nombre.setText(producto.nombre)
        fechaEntrada.setText(producto.fechaEntrada)
        cantidad.setText(producto.cantidad)

        fechaEntrada.isEnabled = false
        cantidad.isEnabled = false

        builder.setView(view)

        builder.setPositiveButton("Actualizar",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                val  base = FirebaseDatabase.getInstance().getReference("Productos")

                val Nombre    = nombre.text.toString().trim()
                val IdInventario = producto.idInventario.trim()
                val FechaEntrada = producto.fechaEntrada.trim()
                val Cantidad = producto.cantidad.trim()

                if (Nombre.isEmpty()){
                    nombre.error = "Ingrese el Nombre del Producto "
                    return
                }
                else
                {
                    val producto = ProductosConstructor(producto.id,IdInventario,Nombre,FechaEntrada,Cantidad)
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