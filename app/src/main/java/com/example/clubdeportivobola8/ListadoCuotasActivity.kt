package com.example.clubdeportivobola8

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListadoCuotasActivity : AppCompatActivity() {

    // --- Declarar variables ---
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CuotaAdapter
    private lateinit var spinnerFiltro: Spinner
    private lateinit var btnVencimientosHoy: Button

    // Obtener la fecha de hoy en el formato correcto
    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val fechaHoy = formatoFecha.format(Date())

    private var listaCompletaDeCuotas = listOf(
        Cuota("Mariano Paez", "Tenis", fechaHoy, "Por vencer"),
        Cuota("Lucia Benitez", "Natación", fechaHoy, "Por vencer"),
        Cuota("Juan Pérez", "Fútbol", "10/10/2025", "Vencida"),
        Cuota("María López", "Tenis", "15/10/2025", "Por vencer"),
        Cuota("Carlos Gómez", "Natación", "25/09/2025", "Vencida"),
        Cuota("Ana Torres", "Básquet", "01/11/2025", "Por vencer"),
        Cuota("Luis Martín", "Fútbol", "05/10/2025", "Pagada")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_cuotas)

        // --- Vincular vistas ---
        recyclerView = findViewById(R.id.rvListadoCuotas)
        spinnerFiltro = findViewById(R.id.spFiltroCuotas)
        btnVencimientosHoy = findViewById(R.id.btnVencimientosHoy)
        val btnVolverMenu: Button = findViewById(R.id.btnVolverMenu)

        // --- Configurar RecyclerView ---
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicialmente, el adaptador se crea con la lista completa.
        adapter = CuotaAdapter(listaCompletaDeCuotas)
        recyclerView.adapter = adapter

        // --- Configurar el Spinner y su lógica ---
        setupSpinner()

        // --- Lógica del botón para Vencimientos de Hoy ---
        btnVencimientosHoy.setOnClickListener {
            filtrarPorVencimientosDeHoy()
        }

        // --- Lógica del botón para volver atrás ---
        btnVolverMenu.setOnClickListener {
            finish()
        }
    }

    private fun setupSpinner() {
        val opcionesFiltro = arrayOf("Mostrar Todas", "Vencida", "Pagada", "Por vencer")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesFiltro)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFiltro.adapter = spinnerAdapter

        spinnerFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val opcionSeleccionada = parent?.getItemAtPosition(position).toString()
                filtrarLista(opcionSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun filtrarLista(estado: String) {
        val listaFiltrada: List<Cuota>

        if (estado == "Mostrar Todas") {
            listaFiltrada = listaCompletaDeCuotas
        } else {
            listaFiltrada = listaCompletaDeCuotas.filter { cuota ->
                cuota.estado == estado
            }
        }
        adapter.actualizarLista(listaFiltrada)
    }

    // Función para filtrar por vencimientos de hoy
    private fun filtrarPorVencimientosDeHoy() {
        val listaFiltrada = listaCompletaDeCuotas.filter { cuota ->
            cuota.fechaVencimiento == fechaHoy
        }

        adapter.actualizarLista(listaFiltrada)
    }
}
