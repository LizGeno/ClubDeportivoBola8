package com.example.clubdeportivobola8

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListadoCuotasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CuotaAdapter
    private lateinit var spinnerFiltro: Spinner
    private lateinit var btnVencimientosHoy: Button
    private lateinit var cuotaDao: CuotaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_cuotas)

        // --- Inicializar la base de datos ---
        cuotaDao = AppDatabase.getDatabase(this).cuotaDao()

        // --- Vincular vistas ---
        recyclerView = findViewById(R.id.rvListadoCuotas)
        spinnerFiltro = findViewById(R.id.spFiltroCuotas)
        btnVencimientosHoy = findViewById(R.id.btnVencimientosHoy)
        val btnVolverMenu: Button = findViewById(R.id.btnVolverMenu)

        // --- Configurar RecyclerView ---
        recyclerView.layoutManager = LinearLayoutManager(this)
        // El adaptador se inicializa vacío. Se llenará cuando los datos se carguen de la BD.
        adapter = CuotaAdapter(emptyList())
        recyclerView.adapter = adapter

        // --- Lógica de la UI ---
        setupSpinner()
        btnVencimientosHoy.setOnClickListener { filtrarPorVencimientosDeHoy() }
        btnVolverMenu.setOnClickListener { finish() }

        // --- Cargar datos y popular la BD si es necesario ---
        lifecycleScope.launch {
            // Si la base de datos está vacía, inserta los datos de ejemplo.
            if (cuotaDao.count() == 0) {
                insertarDatosDeEjemplo()
            }
            // Carga y muestra todas las cuotas al iniciar.
            cargarTodasLasCuotas()
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
        lifecycleScope.launch {
            if (estado == "Mostrar Todas") {
                cargarTodasLasCuotas()
            } else {
                // Implementa la búsqueda por estado si es necesario
                // Por ahora, recargamos todo para simplificar.
                 cuotaDao.getAllCuotas().collect { lista ->
                    adapter.actualizarLista(lista.filter { it.estado == estado })
                }
            }
        }
    }

    private fun filtrarPorVencimientosDeHoy() {
        lifecycleScope.launch {
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fechaHoy = formatoFecha.format(Date())

            cuotaDao.getCuotasByFechaVencimiento(fechaHoy).collect { lista ->
                adapter.actualizarLista(lista)
            }
        }
    }

    private fun cargarTodasLasCuotas() {
        lifecycleScope.launch {
            cuotaDao.getAllCuotas().collect { lista ->
                adapter.actualizarLista(lista)
            }
        }
    }

    private suspend fun insertarDatosDeEjemplo() {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaHoy = formatoFecha.format(Date())

        val cuotasDeEjemplo = listOf(
            Cuota(nombreSocio = "Mariano Paez", actividad = "Tenis", fechaVencimiento = fechaHoy, estado = "Por vencer"),
            Cuota(nombreSocio = "Lucia Benitez", actividad = "Natación", fechaVencimiento = fechaHoy, estado = "Por vencer"),
            Cuota(nombreSocio = "Juan Pérez", actividad = "Fútbol", fechaVencimiento = "10/10/2025", estado = "Vencida"),
            Cuota(nombreSocio = "María López", actividad = "Tenis", fechaVencimiento = "15/10/2025", estado = "Por vencer"),
            Cuota(nombreSocio = "Carlos Gómez", actividad = "Natación", fechaVencimiento = "25/09/2025", estado = "Vencida"),
            Cuota(nombreSocio = "Ana Torres", actividad = "Básquet", fechaVencimiento = "01/11/2025", estado = "Por vencer"),
            Cuota(nombreSocio = "Luis Martín", actividad = "Fútbol", fechaVencimiento = "05/10/2025", estado = "Pagada")
        )
        cuotaDao.insertAll(*cuotasDeEjemplo.toTypedArray())
    }
}