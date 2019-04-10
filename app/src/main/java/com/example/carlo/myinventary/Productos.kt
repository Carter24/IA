package com.example.carlo.myinventary

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

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
        actionBar!!.title = "Inventarios"
        actionBar.setDisplayHomeAsUpEnabled(true)

        idInventario = intent.getStringExtra("id")
        Toast.makeText(this, "$idInventario", Toast.LENGTH_LONG).show()

        val intent2 = Intent(this,ProductosSalidas::class.java)
        intent2.putExtra("id",idInventario)

        producto    = findViewById(R.id.NombreProductoEntrada)
        entrada  = findViewById(R.id.FechaEntrada)
        cantidad   = findViewById(R.id.CantidadEntrada)

        btnAgregar = findViewById(R.id.AgregarEntrada)
        btnlista = findViewById(R.id.VerEntrada)

        btnAgregar.setOnClickListener {
            guardar()
            producto.text.clear()
            entrada.text.clear()
            cantidad.text.clear()
        }
        btnlista.setOnClickListener {
            val intent = Intent(this, ListadoProductos::class.java)
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
                    Toast.makeText(this, "Ha Entrado Un Producto", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu_entradas_salidas: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_entradas_salidas,menu_entradas_salidas)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.opIngresoEntrada->{
                val intent = Intent(this,Productos::class.java)
                intent.putExtra("id",idInventario)
                startActivity(intent)
                super.onOptionsItemSelected(item)
            }

            R.id.opIngresoSalida->{
                val intent = Intent(this,ProductosSalidas::class.java)
                intent.putExtra("id",idInventario)
                startActivity(intent)
                super.onOptionsItemSelected(item)
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

