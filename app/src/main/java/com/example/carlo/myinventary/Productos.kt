package com.example.carlo.myinventary

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_productos.*

class Productos : AppCompatActivity() {

    lateinit var idInventario:String
    lateinit var producto : EditText
    lateinit var entrada : EditText
    lateinit var cantidad : EditText
    lateinit var btnAgregar : Button
    lateinit var btnlista : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val actionBar = supportActionBar
        actionBar!!.title = "My Inventary"
        actionBar.setDisplayHomeAsUpEnabled(true)

        idInventario = intent.getStringExtra("id")
        Toast.makeText(this, "${idInventario}", Toast.LENGTH_LONG).show()

        producto    = findViewById(R.id.NombreProducto)
        entrada  = findViewById(R.id.FechaEntrada)
        cantidad   = findViewById(R.id.Cantidad)

        btnAgregar = findViewById(R.id.Agregar2)
        btnlista = findViewById(R.id.Ver2)

        btnAgregar.setOnClickListener {
            guardar()
        }
        btnlista.setOnClickListener {
            val intent = Intent(this, ListadoProductos::class.java)
            val intent2 = Intent(this,AdapterProducto::class.java)
            intent2.putExtra("idInventario", idInventario)
            intent.putExtra("idInventario", idInventario)
            startActivity(intent)
        }
    }
    private fun guardar(){
        val nombreProducto    = producto.text.toString().trim()
        val fechaEntrada  = entrada.text.toString().trim()
        val Cantidad  = cantidad.text.toString().trim()
        val idInventario = intent.getStringExtra("id")

        if (nombreProducto.isEmpty()){
            producto.error = "Ingrese el dato de forma correcta"
            return
        }
        if (fechaEntrada.isEmpty()){
            entrada.error = "Ingrese el dato de forma correcta"
            return
        }
        if (Cantidad.isEmpty()){
            cantidad.error = "Ingrese el dato de forma correcta"
            return
        }
        else
        {
            val Base = FirebaseDatabase.getInstance().getReference("Productos")
            val id = Base.push().key
            val producto = ProductosConstructor(id!!,idInventario, nombreProducto, fechaEntrada, Cantidad)

            if (id != null)
            {
                Base.child(id).setValue(producto).addOnCanceledListener {
                    Toast.makeText(this, "Has Agregado Un Producto", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

