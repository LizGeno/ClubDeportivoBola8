package com.example.clubdeportivobola8

import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class InscripcionActividadActivity : AppCompatActivity() {

    // 1. Declara las vistas como propiedades de la clase
    private lateinit var etNombreApellido: EditText
    private lateinit var etDni: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etEmail: EditText
    private lateinit var spinnerActividades: Spinner
    private lateinit var btnPagar: Button
    private lateinit var btnVolverMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscripcion_actividad)

        // 2. INICIALIZA las propiedades (sin 'val')
        etNombreApellido = findViewById(R.id.etNombreApellido)
        etDni = findViewById(R.id.etDni)
        etTelefono = findViewById(R.id.etTelefono)
        etEmail = findViewById(R.id.etEmail)
        spinnerActividades = findViewById(R.id.spinnerActividades)
        btnPagar = findViewById(R.id.btnPagar)
        btnVolverMenu = findViewById(R.id.btnVolverMenu)

        // --- Configuración del Spinner ---
        val actividades = arrayOf("Fútbol", "Tenis", "Natación", "Básquet")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, actividades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerActividades.adapter = adapter

        // --- Lógica del botón "Pagar" ---
        btnPagar.setOnClickListener {
            if (validarCampos()) {
                mostrarDialogoConfirmacion()
            }
        }

        // --- Lógica del botón "Volver al Menú" ---
        btnVolverMenu.setOnClickListener {
            finish()
        }
    }

    /**
     * Valida todos los campos del formulario usando las propiedades de la clase.
     */
    private fun validarCampos(): Boolean {
        val nombreApellidoText = etNombreApellido.text.toString().trim()
        val dniText = etDni.text.toString().trim()
        val telefonoText = etTelefono.text.toString().trim()
        val emailText = etEmail.text.toString().trim()

        if (nombreApellidoText.isBlank()) {
            Toast.makeText(this, "El nombre y apellido no puede estar vacío", Toast.LENGTH_SHORT).show()
            return false
        }
        if (dniText.isBlank()) {
            Toast.makeText(this, "El DNI no puede estar vacío", Toast.LENGTH_SHORT).show()
            return false
        }
        if (telefonoText.isBlank()) {
            Toast.makeText(this, "El teléfono no puede estar vacío", Toast.LENGTH_SHORT).show()
            return false
        }
        if (emailText.isBlank()) {
            Toast.makeText(this, "El email no puede estar vacío", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            Toast.makeText(this, "El formato del email no es válido", Toast.LENGTH_SHORT).show()
            return false
        }

        return true // Todos los campos son válidos
    }

    /**
     * Muestra un diálogo de alerta para confirmar el pago.
     */
    private fun mostrarDialogoConfirmacion() {
        AlertDialog.Builder(this)
            .setTitle("Confirmación de Pago")
            .setMessage("Actividad abonada correctamente.")
            .setPositiveButton("Aceptar") { _, _ ->
                finish() // Cierra la actividad y vuelve al menú
            }
            .setCancelable(false)
            .show()
    }
}
