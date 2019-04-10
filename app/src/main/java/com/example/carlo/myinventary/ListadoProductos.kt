package com.example.carlo.myinventary

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.google.firebase.database.*

class ListadoProductos : AppCompatActivity() {

    lateinit var Base : DatabaseReference
    lateinit var listaProducto:MutableList<ProductosConstructor>
    lateinit var listaProducto2:MutableList<ProductosSalidasConstructor>
    lateinit var listView: ListView
    lateinit var query:Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val actionBar = supportActionBar
        actionBar!!.title = "Entradas"
        actionBar.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_listado_productos)
        val idInventario = intent.getStringExtra("idInventario")

        listaProducto = mutableListOf()
        listView = findViewById(R.id.ListaProductos)
        Base = FirebaseDatabase.getInstance().getReference("Productos")
        query = FirebaseDatabase.getInstance().getReference("Productos").orderByChild("idInventario").equalTo(idInventario.toString())

            query.addValueEventListener(object : ValueEventListener {
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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu_lista: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista,menu_lista)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.opEntradas->{
                val actionBar = supportActionBar
                actionBar!!.title = "Entradas"
                actionBar.setDisplayHomeAsUpEnabled(true)

                setContentView(R.layout.activity_listado_productos)
                val idInventario = intent.getStringExtra("idInventario")

                listaProducto = mutableListOf()
                listView = findViewById(R.id.ListaProductos)
                Base = FirebaseDatabase.getInstance().getReference("Productos")
                query = FirebaseDatabase.getInstance().getReference("Productos").orderByChild("idInventario").equalTo(idInventario.toString())

                query.addValueEventListener(object : ValueEventListener {
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

                super.onOptionsItemSelected(item)
            }

            R.id.opSalidas->{

                val actionBar = supportActionBar
                actionBar!!.title = "Salidas"
                actionBar.setDisplayHomeAsUpEnabled(true)

                setContentView(R.layout.activity_listado_productos)
                val idInventario = intent.getStringExtra("idInventario")

                listaProducto2 = mutableListOf()
                listView = findViewById(R.id.ListaProductos)
                Base = FirebaseDatabase.getInstance().getReference("ProductosSalidas")
                query = FirebaseDatabase.getInstance().getReference("ProductosSalidas").orderByChild("idInventario").equalTo(idInventario.toString())

                query.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0!!.exists())
                        {
                            listaProducto2.clear()
                            for (e in p0.children){
                                val producto = e.getValue(ProductosSalidasConstructor::class.java)
                                listaProducto2.add(producto!!)
                            }
                        }
                        val adaptador = AdapterProductoSalidas(this@ListadoProductos,R.layout.producto_salidas_template,listaProducto2)
                        listView.adapter = adaptador
                    }
                })


                super.onOptionsItemSelected(item)
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}