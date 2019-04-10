package com.example.carlo.myinventary

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Registro : AppCompatActivity() {

    lateinit var Usuario : EditText
    lateinit var Contraseña : EditText
    lateinit var btnAgregar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val actionBar = supportActionBar
        actionBar!!.title = "Login"
        actionBar.setDisplayHomeAsUpEnabled(true)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        Usuario = findViewById(R.id.Usuario)
        Contraseña = findViewById(R.id.Contraseña)
        btnAgregar = findViewById(R.id.AgregarUsuario)

        btnAgregar.setOnClickListener {
            guardar()
            Usuario.text.clear()
            Contraseña.text.clear()
        }
    }

    private fun guardar(){
        val usuario    = Usuario.text.toString().trim()
        val contraseña  = Contraseña.text.toString().trim()

        if (usuario.isEmpty()){
            Usuario.error = "Ingrese el dato de forma correcta"
            return
        }
        if (contraseña.isEmpty()){
            Contraseña.error = "Ingrese el dato de forma correcta"
            return
        }

        val Base = FirebaseDatabase.getInstance().getReference("Usuarios")
        val id = Base.push().key!!
        val usuarios = UsuariosConstructor(id!!, usuario, contraseña)

        if (id != null)
        {
            Base.child(id).setValue(usuarios).addOnCanceledListener {
                Toast.makeText(this, "Ya Estás Registrado", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
