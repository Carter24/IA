package com.example.carlo.myinventary

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.firebase.database.*

class ListadoInventarios : AppCompatActivity() {

    lateinit var Base : DatabaseReference
    lateinit var listainventario:MutableList<Inventarios>
    lateinit var listview: ListView
    lateinit var boton : TextView
    lateinit var TextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_inventarios)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val actionBar = supportActionBar
        actionBar!!.title = "My Inventary"
        actionBar.setDisplayHomeAsUpEnabled(true)

        listainventario = mutableListOf()
        listview = findViewById(R.id.ListaInventarios)
        Base = FirebaseDatabase.getInstance().getReference("Inventarios")

        Base.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists())
                {
                    listainventario.clear()
                    for (e in p0.children){
                        val inventario = e.getValue(Inventarios::class.java)
                        listainventario.add(inventario!!)
                    }
                }
                val adaptador = Adapter(this@ListadoInventarios,R.layout.inventary_template, listainventario)
                listview.adapter = adaptador
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
