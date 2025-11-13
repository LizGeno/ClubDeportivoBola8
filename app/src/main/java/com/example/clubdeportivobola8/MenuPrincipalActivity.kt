package com.example.clubdeportivobola8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton

class MenuPrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val btnBack = findViewById<ImageButton>(R.id.btnBackMenu)
        btnBack.setOnClickListener {
            finish() // Vuelve a la pantalla anterior (Login)
        }

        // 1. Encontrar el botón para registrar socio por su ID
        val btnSocio = findViewById<AppCompatButton>(R.id.btnSocio)


        btnSocio.setOnClickListener {
            val intent = Intent(this, AltaSocioActivity::class.java)

            startActivity(intent)
        }
        val btnPagarCuota = findViewById<AppCompatButton>(R.id.btnPagarCuota)
        btnPagarCuota.setOnClickListener {
            val intent = Intent(this, PagarCuotaActivity::class.java)
            startActivity(intent)
        }

<<<<<<< HEAD
        // 🔹 Botón Actividades No Socios
        val btnActividadesNoSocios = findViewById<AppCompatButton>(R.id.btnActividadesNoSocios)
        btnActividadesNoSocios.setOnClickListener {
            // Crea una "intención" para abrir la nueva actividad
            val intent = Intent(this, InscripcionActividadActivity::class.java)
            // Ejecuta la intención, abriendo la pantalla
            startActivity(intent)
        }


        // 🔹 Botón Lista de Cuotas
        val btnListaCuotas = findViewById<AppCompatButton>(R.id.btnListaCuotas)
        btnListaCuotas.setOnClickListener {
            val intent = Intent(this, ListadoCuotasActivity::class.java)
            startActivity(intent)
        }
=======
        val btnListaSocios = findViewById<AppCompatButton>(R.id.btnListaSocios)
        btnListaSocios.setOnClickListener {
            val intent = Intent(this, ListadoSociosActivity::class.java)
            startActivity(intent)
        }

        val btnActividadesNoSocios = findViewById<AppCompatButton>(R.id.btnActividadesNoSocios)
        btnActividadesNoSocios.setOnClickListener {
            val intent = Intent(this, InscripcionActividadActivity::class.java)
            startActivity(intent)
        }

>>>>>>> feature/db_socios_nosocios
    }

}
