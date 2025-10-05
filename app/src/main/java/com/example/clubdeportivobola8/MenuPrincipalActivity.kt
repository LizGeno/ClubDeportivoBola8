package com.example.clubdeportivobola8

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MenuPrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        // 🔹 Botón para volver atrás
        val btnBack = findViewById<ImageButton>(R.id.btnBackMenu)
        btnBack.setOnClickListener {
            finish() // Vuelve a la pantalla anterior (Login)
        }

        // 🔹 Botón Actividades No Socios
        val btnActividadesNoSocios = findViewById<Button>(R.id.btnActividadesNoSocios)
        btnActividadesNoSocios.setOnClickListener {
            // Crea una "intención" para abrir la nueva actividad
            val intent = Intent(this, InscripcionActividadActivity::class.java)
            // Ejecuta la intención, abriendo la pantalla
            startActivity(intent)
        }


        // 🔹 Botón Lista de Cuotas
        val btnListaCuotas = findViewById<Button>(R.id.btnListaCuotas)
        btnListaCuotas.setOnClickListener {
            val intent = Intent(this, ListadoCuotasActivity::class.java)
            startActivity(intent)
        }
    }
}

