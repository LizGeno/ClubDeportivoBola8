package com.example.clubdeportivobola8

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivobola8.data.DBHelper
import com.example.clubdeportivobola8.databinding.ActivityPagarCuotaBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PagarCuotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagarCuotaBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagarCuotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DBHelper.getInstance(this) // Inicializamos el DBHelper (Singleton)

        // --- Configuración de la Toolbar ---
        val toolbar = binding.baseLayout.toolbar
        toolbar.title = "Pagar Cuota"
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {
            finish() // Cierra la actividad y vuelve al menú
        }

        // --- Llamado de funciones ---
        configurarSpinnerMeses()
        configurarDatePicker()
        configurarBusquedaPorDNI()

        // --- Lógica del botón de pago ---
        binding.buttonConfirmarPago.setOnClickListener {
            realizarPago()
        }
    }

    private fun configurarSpinnerMeses() {
        val meses = arrayOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, meses)
        binding.autoCompleteMes.setAdapter(adapter)
    }

    private fun realizarPago() {
        if (!validarCampos()) {
            return
        }
        val dniSocio = binding.textFieldBuscarSocio.editText?.text.toString()
        val mesPagado = binding.autoCompleteMes.text.toString()
        val montoString = binding.textFieldMonto.editText?.text.toString()
        val fechaString = binding.textFieldFecha.editText?.text.toString()

        val monto = montoString.toDoubleOrNull() ?: 0.0
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var fechaPago: Date
        try {
            fechaPago = formatoFecha.parse(fechaString) ?: Date()
        }catch (e: Exception) {
            e.printStackTrace()
            fechaPago = Date()
        }
        val exito = dbHelper.insertarPago(
            dniSocio = dniSocio,
            fechaPago = fechaPago,
            mesPagado = mesPagado,
            monto = monto
        )

        if (exito) {
            mostrarDialogoExito()
        } else {
            mostrarDialogoError("No se pudo registrar el pago. Verifique que el DNI del socio sea correcto.")
        }
    }

    private fun validarCampos(): Boolean {
        var esValido = true
        // Limpiamos errores previos
        binding.textFieldBuscarSocio.error = null
        binding.textFieldMes.error = null
        binding.textFieldMonto.error = null
        binding.textFieldFecha.error = null

        // Validación DNI
        if (binding.textFieldBuscarSocio.editText?.text.isNullOrBlank()) {
            binding.textFieldBuscarSocio.error = "El DNI del socio es obligatorio"
            esValido = false
        }

        // Validación Mes
        if (binding.autoCompleteMes.text.isNullOrBlank()) {
            binding.textFieldMes.error = "Debe seleccionar un mes"
            esValido = false
        }
        // Validación Monto (NUEVO)
        val montoStr = binding.textFieldMonto.editText?.text.toString()
        if (montoStr.isNullOrBlank()) {
            binding.textFieldMonto.error = "El monto es obligatorio"
            esValido = false
        } else if (montoStr.toDoubleOrNull() == null) {
            // Comprobamos que se pueda convertir a Double
            binding.textFieldMonto.error = "El monto debe ser un número válido (ej: 5000.00)"
            esValido = false
        }
        // Validación Fecha (NUEVO)
        // Asumiendo que el formato es dd/MM/yyyy
        val fechaStr = binding.textFieldFecha.editText?.text.toString()
        if (fechaStr.isNullOrBlank()) {
            binding.textFieldFecha.error = "La fecha es obligatoria"
            esValido = false
        } else if (!fechaStr.matches(Regex("""^\d{2}/\d{2}/\d{4}$"""))) {
            // Regex simple para validar el formato.
            binding.textFieldFecha.error = "Formato de fecha debe ser dd/MM/yyyy"
            esValido = false
        }

        return esValido
    }

    private fun mostrarDialogoExito() {
        val dniSocio = binding.textFieldBuscarSocio.editText?.text.toString()
        val mesPagado = binding.autoCompleteMes.text.toString()

        AlertDialog.Builder(this)
            .setTitle("Pago Exitoso")
            .setMessage("Se registró el pago de la cuota de $mesPagado para el socio con DNI: $dniSocio.")
            .setPositiveButton("Aceptar") { _, _ ->
                finish() // Cierra la actividad y vuelve al menú
            }
            .setCancelable(false)
            .show()
    }
    private fun mostrarDialogoError(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Error en el Pago")
            .setMessage(mensaje)
            .setPositiveButton("Intentar de nuevo", null) // 'null' significa que solo cierra el diálogo
            .show()
    }

    private fun configurarDatePicker() {
        val calendario = java.util.Calendar.getInstance()

        val dateSetListener = android.app.DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

            val mes = month + 1
            val fechaFormateada = String.format("%02d/%02d/%d", dayOfMonth, mes, year)
            binding.textFieldFecha.editText?.setText(fechaFormateada)
        }

        binding.textFieldFecha.editText?.setOnClickListener {
            android.app.DatePickerDialog(
                this,
                dateSetListener,
                calendario.get(java.util.Calendar.YEAR),
                calendario.get(java.util.Calendar.MONTH),
                calendario.get(java.util.Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    //Funcion que confirma identidad en la busqueda por DNI
    private fun configurarBusquedaPorDNI() {
        binding.textFieldBuscarSocio.editText?.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {
                val dni = binding.textFieldBuscarSocio.editText?.text.toString()
                if (dni.isNotBlank()) {
                    val socio = dbHelper.getSocioByDni(dni)

                    if (socio != null) {
                        binding.textFieldBuscarSocio.helperText = "Socio: ${socio.nombre} ${socio.apellido}"
                        binding.textFieldBuscarSocio.error = null // Borramos cualquier error previo
                    } else {
                        binding.textFieldBuscarSocio.helperText = null // Borramos el helper
                        binding.textFieldBuscarSocio.error = "No se encontró ningún socio con ese DNI"
                    }
                }
            } else {
                binding.textFieldBuscarSocio.helperText = null
            }
        }
    }
}
