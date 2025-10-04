package com.example.clubdeportivobola8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MenuPrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val btnBack = findViewById<ImageButton>(R.id.btnBackMenu)
        btnBack.setOnClickListener {
            finish() // Vuelve a la pantalla anterior (Login)
        }
    }



}