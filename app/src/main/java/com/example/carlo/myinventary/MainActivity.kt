package com.example.carlo.myinventary

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var Inventario : EditText
    lateinit var Descripcion : EditText
    lateinit var btnAgregar : Button
    lateinit var btnlista : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        Inventario    = findViewById(R.id.editText)
        Descripcion   = findViewById(R.id.editText2)

        btnAgregar = findViewById(R.id.Agregar)
        btnlista = findViewById(R.id.Ver)

        btnAgregar.setOnClickListener {
            guardar()
        }

        btnlista.setOnClickListener {
            val intent = Intent(this, ListadoInventarios::class.java)
            startActivity(intent)
        }
    }
    private fun guardar(){
        val nombreInventario    = Inventario.text.toString().trim()
        val descripcion  = Descripcion.text.toString().trim()

        if (nombreInventario.isEmpty()){
            Inventario.error = "Ingrese el dato de forma correcta"
            return
        }
        if (descripcion.isEmpty()){
            Descripcion.error = "Ingrese el dato de forma correcta"
            return
        }

        val Base = FirebaseDatabase.getInstance().getReference("Inventarios")
        val id = Base.push().key!!
        val inventario = Inventarios(id!!, nombreInventario, descripcion)

        if (id != null)
        {
            Base.child(id).setValue(inventario).addOnCanceledListener {
                Toast.makeText(this, "Has Agregado Un Inventario", Toast.LENGTH_LONG).show()
            }
        }
    }
}