package com.example.clubdeportivobola8

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.clubdeportivobola8.data.DBHelper
import com.example.clubdeportivobola8.data.model.Socio
import com.example.clubdeportivobola8.databinding.ActivityAltaSocioBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AltaSocioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAltaSocioBinding
    private lateinit var dbHelper: DBHelper

    // Variable para guardar la fecha seleccionada
    private var fechaSeleccionada: Date? = null

    // Variable para saber si estamos en modo edición y guardar el socio actual
    private var socioActual: Socio? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAltaSocioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DBHelper.getInstance(this)

        // 1. COMPROBAR SI ESTAMOS EN MODO EDICIÓN
        val socioId = intent.getIntExtra("SOCIO_ID", -1)
        if (socioId != -1) {
            // Estamos en modo edición
            lifecycleScope.launch(Dispatchers.IO) {
                socioActual = dbHelper.getSocioById(socioId)
                withContext(Dispatchers.Main) {
                    socioActual?.let { socio ->
                        prepararUIparaEdicion(socio)
                    }
                }
            }
        } else {
            // Estamos en modo creación
            configurarUIparaCreacion()
        }

        // Lógica del calendario (no cambia)
        binding.textFieldFechaNacimiento.editText?.isFocusable = false
        binding.textFieldFechaNacimiento.editText?.isClickable = true
        binding.textFieldFechaNacimiento.editText?.setOnClickListener {
            mostrarDialogoCalendario()
        }

        // 2. LÓGICA DEL BOTÓN GUARDAR/ACTUALIZAR
        binding.buttonAltaSocio.setOnClickListener {
            if (validarCampos()) {
                val socioEditado = Socio(
                    // Si estamos editando, usamos el id existente, si no, se usará el autoincremental (id=0)
                    id = socioActual?.id ?: 0,
                    nombre = binding.textFieldNombre.editText!!.text.toString(),
                    apellido = binding.textFieldApellido.editText!!.text.toString(),
                    dni = binding.textFieldDNI.editText!!.text.toString(),
                    fechaNacimiento = fechaSeleccionada!!,
                    email = binding.textFieldCorreo.editText!!.text.toString()
                )

                // Decidir si actualizar o registrar
                if (socioActual != null) {
                    actualizarSocioEnBD(socioEditado)
                } else {
                    registrarSocioEnBD(socioEditado)
                }
            }
        }
    }

    private fun configurarUIparaCreacion() {
        binding.baseLayout.toolbar.title = "Alta de Nuevo Socio"
        binding.buttonAltaSocio.text = "Registrar Socio"
        binding.baseLayout.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun prepararUIparaEdicion(socio: Socio) {
        // Cambiar título y texto del botón
        binding.baseLayout.toolbar.title = "Editar Socio"
        binding.buttonAltaSocio.text = "Actualizar Datos"
        binding.baseLayout.toolbar.setNavigationOnClickListener { finish() }

        // Rellenar los campos con los datos del socio
        binding.textFieldNombre.editText?.setText(socio.nombre)
        binding.textFieldApellido.editText?.setText(socio.apellido)
        binding.textFieldDNI.editText?.setText(socio.dni)
        binding.textFieldCorreo.editText?.setText(socio.email)

        // Rellenar la fecha
        fechaSeleccionada = socio.fechaNacimiento
        val formatoVisible = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        binding.textFieldFechaNacimiento.editText?.setText(formatoVisible.format(socio.fechaNacimiento))
    }

    // --- NUEVA FUNCIÓN PARA ACTUALIZAR ---
    private fun actualizarSocioEnBD(socio: Socio) {
        lifecycleScope.launch(Dispatchers.IO) {
            val exito = dbHelper.updateSocio(socio)
            withContext(Dispatchers.Main) {
                if (exito) {
                    Toast.makeText(this@AltaSocioActivity, "Socio actualizado con éxito", Toast.LENGTH_SHORT).show()
                    finish() // Volver al listado
                } else {
                    mostrarDialogoError("No se pudo actualizar al socio.")
                }
            }
        }
    }

    // --- El resto de funciones (registrar, validar, diálogos, etc.) no necesitan grandes cambios ---

    private fun registrarSocioEnBD(socio: Socio) {
        lifecycleScope.launch(Dispatchers.IO) {
            val exito = dbHelper.insertSocio(socio)
            withContext(Dispatchers.Main) {
                if (exito) {
                    mostrarDialogoExito()
                } else {
                    mostrarDialogoError("No se pudo registrar al socio. El DNI podría estar duplicado.")
                }
            }
        }
    }

    private fun mostrarDialogoError(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun mostrarDialogoExito() {
        AlertDialog.Builder(this)
            .setTitle("Registro Exitoso")
            .setMessage("Socio registrado con éxito.")
            .setPositiveButton("Aceptar") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    // Tu función mostrarDialogoCalendario() no necesita cambios
    private fun mostrarDialogoCalendario() {
        val calendario = Calendar.getInstance()
        // Si ya hay una fecha seleccionada (modo edición), la usamos como punto de partida
        fechaSeleccionada?.let { calendario.time = it }

        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, yearSeleccionado, mesSeleccionado, diaSeleccionado ->
                val calendarioSeleccionado = Calendar.getInstance()
                calendarioSeleccionado.set(yearSeleccionado, mesSeleccionado, diaSeleccionado)
                fechaSeleccionada = calendarioSeleccionado.time

                val formatoVisible = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                binding.textFieldFechaNacimiento.editText?.setText(formatoVisible.format(fechaSeleccionada!!))
                binding.textFieldFechaNacimiento.error = null
            },
            anio, mes, dia
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    // Tu función validarCampos() no necesita cambios
    private fun validarCampos(): Boolean {
        var esValido = true
        binding.textFieldNombre.error = null
        binding.textFieldApellido.error = null
        binding.textFieldDNI.error = null
        binding.textFieldFechaNacimiento.error = null

        if (binding.textFieldNombre.editText?.text.isNullOrBlank()) {
            binding.textFieldNombre.error = "El nombre es obligatorio"
            esValido = false
        }
        if (binding.textFieldApellido.editText?.text.isNullOrBlank()) {
            binding.textFieldApellido.error = "El apellido es obligatorio"
            esValido = false
        }
        if (binding.textFieldDNI.editText?.text.isNullOrBlank()) {
            binding.textFieldDNI.error = "El DNI es obligatorio"
            esValido = false
        }
        if (fechaSeleccionada == null) {
            binding.textFieldFechaNacimiento.error = "Debe seleccionar una fecha"
            esValido = false
        }
        return esValido
    }
}
