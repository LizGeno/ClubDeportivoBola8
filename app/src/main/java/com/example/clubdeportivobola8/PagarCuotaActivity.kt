package com.example.clubdeportivobola8

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivobola8.databinding.ActivityPagarCuotaBinding

class PagarCuotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagarCuotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagarCuotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Configuración de la Toolbar ---
        val toolbar = binding.baseLayout.toolbar
        toolbar.title = "Pagar Cuota"
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {
            finish() // Cierra la actividad y vuelve al menú
        }

        // --- Configuración del Spinner de Meses ---
        configurarSpinnerMeses()

        // --- Lógica del botón de pago ---
        binding.buttonConfirmarPago.setOnClickListener {
            if (validarCampos()) {
                mostrarDialogoExito()
            }
        }
    }

    private fun configurarSpinnerMeses() {
        val meses = arrayOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, meses)
        binding.autoCompleteMes.setAdapter(adapter)
    }

    private fun validarCampos(): Boolean {
        var esValido = true
        binding.textFieldBuscarSocio.error = null
        binding.textFieldMes.error = null

        if (binding.textFieldBuscarSocio.editText?.text.isNullOrBlank()) {
            binding.textFieldBuscarSocio.error = "El DNI del socio es obligatorio"
            esValido = false
        }

        if (binding.autoCompleteMes.text.isNullOrBlank()) {
            binding.textFieldMes.error = "Debe seleccionar un mes"
            // Es importante poner el error en el TextInputLayout para que se muestre correctamente
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
}
