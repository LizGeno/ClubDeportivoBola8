package com.example.clubdeportivobola8

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivobola8.R.id

class InscripcionActividadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscripcion_actividad)

        // 🔹 Botón Actividades No Socios
        val btnActividadesNoSocios = findViewById<Button>(id.btnActividadesNoSocios)
        btnActividadesNoSocios.setOnClickListener {
            val intent = Intent(this, InscripcionActividadActivity::class.java)
            startActivity(intent)
        }

        // 🔹 Botón Lista de Cuotas
        val btnListaCuotas = findViewById<Button>(id.btnListaCuotas)
        btnListaCuotas.setOnClickListener {
            val intent = Intent(this, ListadoCuotasActivity::class.java)
            startActivity(intent)
        }

        // 🔹 Botón para volver atrás
        val btnBack = findViewById<ImageButton>(id.btnVolverMenu)
        btnBack.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
        }

    }
}
