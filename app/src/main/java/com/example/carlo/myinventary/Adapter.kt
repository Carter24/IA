package com.example.carlo.myinventary

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.inventary_template.view.*

class Adapter(val ctx : Context, val layoutId:Int, val listaInventario:List<Inventarios>)
    : ArrayAdapter<Inventarios>(ctx,layoutId,listaInventario){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(layoutId,null)

        val nombre = view.findViewById<TextView>(R.id.textView)
        val descripcion = view.findViewById<TextView>(R.id.textView2)
        val id = view.findViewById<TextView>(R.id.textView3)

        val actualizar = view.findViewById<TextView>(R.id.Actualizar)
        val borrar = view.findViewById<TextView>(R.id.Eliminar)
        val inventario = listaInventario[position]
        val btn = view.findViewById<View>(R.id.ver)

       id.text = inventario.id
       btn.setOnClickListener(View.OnClickListener { v ->
            v.context.startActivity(
                Intent(
                    (v.context),
                    (Productos::class.java)
                ).putExtra("id", id.text.toString())
            )
        })

        nombre.text = inventario.nombre
        descripcion.text = inventario.descripcion

        actualizar.setOnClickListener {
            Actualizar(inventario)
        }

        borrar.setOnClickListener {
            Borrar(inventario)
        }
        return view
    }

    private  fun Actualizar(inventario: Inventarios){

        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Actualizar")
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.actualizar,null)

        val nombre = view.findViewById<TextView>(R.id.upNombreProducto)
        val descripcion = view.findViewById<TextView>(R.id.upDescripcion)

        nombre.setText(inventario.nombre)
        descripcion.setText(inventario.descripcion)

        builder.setView(view)

        builder.setPositiveButton("Actualizar",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                val  base = FirebaseDatabase.getInstance().getReference("Inventarios")

                val Nombre    = nombre.text.toString().trim()
                val Descripcion  = descripcion.text.toString().trim()

                if (Nombre.isEmpty()){
                    nombre.error = "Ingrese el Nombre "
                    return
                }
                if (Descripcion.isEmpty()){
                    descripcion.error = "Ingrese la Descripcion"
                    return
                }

                val inventario = Inventarios(inventario.id,Nombre,Descripcion)
                base.child(inventario.id).setValue(inventario)
                Toast.makeText(ctx,"Actualizar", Toast.LENGTH_LONG).show()
            }})

        builder.setNegativeButton("Cancelar",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })
        val alert = builder.create()
        alert.show()
    }

    private fun Borrar(inventario: Inventarios){
        val Base = FirebaseDatabase.getInstance().getReference("Inventarios")
        Base.child(inventario.id).removeValue()
        Toast.makeText(ctx,"Eliminado", Toast.LENGTH_LONG).show()
    }
}

