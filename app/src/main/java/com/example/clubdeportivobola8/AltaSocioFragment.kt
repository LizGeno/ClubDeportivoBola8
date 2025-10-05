package com.example.clubdeportivobola8

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivobola8.databinding.FragmentAltaSocioBinding

// 1. Cambiamos la herencia a AppCompatActivity
class AltaSocioActivity : AppCompatActivity() {

    private lateinit var binding: FragmentAltaSocioBinding

    // 3. Usamos el método 'onCreate' de la Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflamos el layout y lo asignamos como la vista de la actividad
        binding = FragmentAltaSocioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.baseLayout.toolbar
        toolbar.title = "Alta de nuevo socio"
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material) // Icono de flecha estándar
        toolbar.setNavigationOnClickListener {

            finish()
        }


        // --- Lógica del botón de alta ---
        binding.buttonAltaSocio.setOnClickListener {
            // Aquí puedes añadir validaciones de los campos antes de continuar
            if (validarCampos()) {
                mostrarDialogoExito()
            }
        }
    }

    // --- Funciones de ayuda ---

    private fun mostrarDialogoExito() {
        AlertDialog.Builder(this)
            .setTitle("Registro Exitoso")
            .setMessage("Socio registrado con éxito.")
            .setPositiveButton("Aceptar") { dialog, _ ->
                // Al presionar "Aceptar", cerramos esta actividad para volver al menú principal.
                finish()
            }
            .setCancelable(false) // El usuario debe presionar "Aceptar"
            .show()
    }

    private fun validarCampos(): Boolean {
        var esValido = true
        // Limpiamos errores previos
        binding.textFieldNombre.error = null
        binding.textFieldApellido.error = null
        binding.textFieldDNI.error = null

        // Validación de Nombre
        if (binding.textFieldNombre.editText?.text.isNullOrBlank()) {
            binding.textFieldNombre.error = "El nombre es obligatorio"
            esValido = false
        }
        // Validación de Apellido
        if (binding.textFieldApellido.editText?.text.isNullOrBlank()) {
            binding.textFieldApellido.error = "El apellido es obligatorio"
            esValido = false
        }
        // Validación de DNI
        if (binding.textFieldDNI.editText?.text.isNullOrBlank()) {
            binding.textFieldDNI.error = "El DNI es obligatorio"
            esValido = false
        }
        // Puedes agregar más validaciones aquí...

        return esValido
    }
}
