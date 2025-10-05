package com.example.clubdeportivobola8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
// No necesitas importar androidx.fragment.app.Fragment aquí

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

        // 2. Configurar el OnClickListener
        btnSocio.setOnClickListener {
            // 3. Crear un Intent para iniciar AltaSocioActivity
            // ASEGÚRATE DE QUE LA CLASE ES UNA ACTIVITY
            val intent = Intent(this, AltaSocioActivity::class.java)

            // 4. Iniciar la nueva actividad
            startActivity(intent)
        }
        val btnPagarCuota = findViewById<AppCompatButton>(R.id.btnPagarCuota)
        btnPagarCuota.setOnClickListener {
            val intent = Intent(this, PagarCuotaActivity::class.java)
            startActivity(intent)
        }
    }

    // La función navigateTo para Fragments no es necesaria en esta Activity,
    // ya que solo lanza otras Activities. Se puede eliminar para evitar confusiones.
    /*
    private fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
    */
}
