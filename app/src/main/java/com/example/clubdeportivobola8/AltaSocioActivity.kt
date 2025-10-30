package com.example.clubdeportivobola8

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope // Importante
import com.example.clubdeportivobola8.data.DBHelper
import com.example.clubdeportivobola8.data.model.Socio
import com.example.clubdeportivobola8.databinding.ActivityAltaSocioBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 1. Cambiamos la herencia a AppCompatActivity
class AltaSocioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAltaSocioBinding
    private lateinit var dbHelper: DBHelper // Instancia del DBHelper

    // 3. Usamos el método 'onCreate' de la Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflamos el layout y lo asignamos como la vista de la actividad
        binding = ActivityAltaSocioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtenemos la instancia Singleton del DBHelper
        dbHelper = DBHelper.getInstance(this)

        val toolbar = binding.baseLayout.toolbar
        toolbar.title = "Alta de nuevo socio"
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {
            finish()
        }


        // --- Lógica del botón de alta ---
        binding.buttonAltaSocio.setOnClickListener {
            // Aquí puedes añadir validaciones de los campos antes de continuar
            if (validarCampos()) {
                // Creamos el objeto Socio con los datos de la UI
                val nuevoSocio = Socio(
                    nombre = binding.textFieldNombre.editText!!.text.toString(),
                    apellido = binding.textFieldApellido.editText!!.text.toString(),
                    dni = binding.textFieldDNI.editText!!.text.toString(),
                    // Asumimos que los campos de fecha y email existen en tu layout
                    fechaNacimiento = binding.textFieldFechaNacimiento.editText!!.text.toString(),
                    email = binding.textFieldCorreo.editText!!.text.toString()
                )
                registrarSocioEnBD(nuevoSocio)
            }
        }
    }

    // --- Funciones de ayuda ---
    private fun registrarSocioEnBD(socio: Socio) {
        // Usamos lifecycleScope para lanzar una coroutine que sobrevivirá mientras la Activity esté viva
        lifecycleScope.launch(Dispatchers.IO) { // .IO es el dispatcher optimizado para operaciones de disco/red
            val exito = dbHelper.insertSocio(socio)

            // Volvemos al hilo principal para mostrar el resultado en la UI
            withContext(Dispatchers.Main) {
                if (exito) {
                    mostrarDialogoExito()
                } else {
                    mostrarDialogoError()
                }
            }
        }
    }

    private fun mostrarDialogoError() {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("No se pudo registrar al socio. Por favor, inténtelo de nuevo.")
            .setPositiveButton("Aceptar", null)
            .show()
    }
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