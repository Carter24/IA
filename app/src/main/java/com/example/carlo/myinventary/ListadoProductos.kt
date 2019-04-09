package com.example.carlo.myinventary

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class ListadoProductos : AppCompatActivity() {

    lateinit var Base : DatabaseReference
    lateinit var listaProducto:MutableList<ProductosConstructor>
    lateinit var listView: ListView
    lateinit var query:Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val actionBar = supportActionBar
        actionBar!!.title = "My Inventary"
        actionBar.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_listado_productos)
        val idInventario = intent.getStringExtra("idInventario")

        listaProducto = mutableListOf()
        listView = findViewById(R.id.ListaProductos)
        Base = query as DatabaseReference
        query = FirebaseDatabase.getInstance().getReference("Productos").orderByChild("id").equalTo(idInventario.toString())

        Base.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists())
                {
                    listaProducto.clear()
                    for (e in p0.children){
                        val producto = e.getValue(ProductosConstructor::class.java)
                        listaProducto.add(producto!!)
                    }
                }
                val adaptador = AdapterProducto(this@ListadoProductos,R.layout.producto_template,listaProducto)
                listView.adapter = adaptador
            }
        })

    }
}