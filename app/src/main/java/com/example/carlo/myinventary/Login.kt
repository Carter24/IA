package com.example.carlo.myinventary

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {

    lateinit var Usuario : EditText
    lateinit var Contraseña : EditText
    lateinit var btnIngresar : Button
    lateinit var Registrar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        Usuario = findViewById(R.id.UsuarioLogin)
        Contraseña = findViewById(R.id.ContraseñaLogin)
        btnIngresar = findViewById(R.id.Entrar)
        Registrar = findViewById(R.id.RegistroLogin)


        btnIngresar.setOnClickListener {

            if(Usuario != null && Contraseña != null){
                val usuario = Usuario.text.toString()
                val contraseña = Usuario.text.toString()
                val query = (FirebaseDatabase.getInstance().getReference("Usuarios").orderByChild("usuario").equalTo(usuario)).toString()
                val query2 = (FirebaseDatabase.getInstance().getReference("Usuarios").orderByChild("contraseña").equalTo(usuario)).toString()
                if (query == usuario && query2 == contraseña)
                {
                    val intent = Intent(this, MainActivity::class.java )
                    startActivity(intent)
                }
                else
                {
                    val intent = Intent(this, MainActivity::class.java )
                    startActivity(intent)
                }
            }
            else
            {
                Toast.makeText(this, "Hay campos vacíos",Toast.LENGTH_LONG).show()
            }
        }
        Registrar.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
}
