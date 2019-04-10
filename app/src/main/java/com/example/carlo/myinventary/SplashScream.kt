package com.example.carlo.myinventary

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashScream : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scream)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        Handler().postDelayed({
            val intent = Intent(this@SplashScream, Login::class.java)
            startActivity(intent)
            finish()
        },4000)


    }
}
