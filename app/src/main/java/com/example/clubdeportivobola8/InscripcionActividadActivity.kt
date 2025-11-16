package com.example.clubdeportivobola8

import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class InscripcionActividadActivity : AppCompatActivity() {

    private lateinit var tilNombreApellido: TextInputLayout
    private lateinit var tilDni: TextInputLayout
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var spinnerActividades: Spinner
    private lateinit var btnPagar: Button
    private lateinit var btnVolver: Button // Declaramos el nuevo botón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscripcion_actividad)

        // --- Inicialización de vistas ---
        tilNombreApellido = findViewById(R.id.etNombreApellido)
        tilDni = findViewById(R.id.etDni)
        tilTelefono = findViewById(R.id.etTelefono)
        tilEmail = findViewById(R.id.etEmail)
        spinnerActividades = findViewById(R.id.spinnerActividades)
        btnPagar = findViewById(R.id.btnPagar)
        btnVolver = findViewById(R.id.btnVolver) // Inicializamos el nuevo botón

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

        // --- Lógica del nuevo botón "Volver" ---
        btnVolver.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la anterior
        }
    }

    private fun validarCampos(): Boolean {
        val nombreApellidoText = tilNombreApellido.editText?.text.toString().trim()
        val dniText = tilDni.editText?.text.toString().trim()
        val telefonoText = tilTelefono.editText?.text.toString().trim()
        val emailText = tilEmail.editText?.text.toString().trim()

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

        return true
    }

    private fun mostrarDialogoConfirmacion() {
        AlertDialog.Builder(this)
            .setTitle("Confirmación de Pago")
            .setMessage("Actividad abonada correctamente.")
            .setPositiveButton("Aceptar") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
