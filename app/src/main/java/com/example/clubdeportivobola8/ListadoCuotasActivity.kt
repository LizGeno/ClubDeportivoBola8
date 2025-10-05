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

class ListadoCuotasActivity : AppCompatActivity() {

    // --- Declarar variables ---
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CuotaAdapter
    private lateinit var spinnerFiltro: Spinner

    // 🔥 CAMBIO 1: Lista maestra de cuotas. Esta lista contiene TODAS las cuotas.
    private var listaCompletaDeCuotas = listOf(
        Cuota("Juan Pérez", "Fútbol", "10/10/2025", "Vencida"),
        Cuota("María López", "Tenis", "15/10/2025", "Por vencer"),
        Cuota("Carlos Gómez", "Natación", "25/09/2025", "Vencida"),
        Cuota("Ana Torres", "Básquet", "01/11/2025", "Por vencer"),
        Cuota("Luis Martín", "Fútbol", "05/10/2025", "Pagada") // Agregué una pagada para el ejemplo
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nota: He quitado el "enableEdgeToEdge" y su listener para simplificar.
        // Si lo necesitas, puedes volver a añadirlo sin problema.
        setContentView(R.layout.activity_listado_cuotas)

        // --- Vincular vistas ---
        recyclerView = findViewById(R.id.rvListadoCuotas)
        spinnerFiltro = findViewById(R.id.spFiltroCuotas)
        val btnVolverMenu: Button = findViewById(R.id.btnVolverMenu)

        // --- Configurar RecyclerView ---
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 🔥 CAMBIO 2: Inicialmente, el adaptador se crea con la lista completa.
        adapter = CuotaAdapter(listaCompletaDeCuotas)
        recyclerView.adapter = adapter

        // --- Configurar el Spinner y su lógica ---
        setupSpinner()

        // --- Lógica del botón para volver atrás ---
        btnVolverMenu.setOnClickListener {
            // Usamos finish() que es más eficiente para volver a la pantalla anterior.
            finish()
        }
    }

    // 🔥 CAMBIO 3: Nueva función para configurar el Spinner
    private fun setupSpinner() {
        // Opciones que se mostrarán en el Spinner
        val opcionesFiltro = arrayOf("Mostrar Todas", "Vencida", "Pagada", "Por vencer")

        // Crear un ArrayAdapter para el Spinner
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesFiltro)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFiltro.adapter = spinnerAdapter

        // **¡AQUÍ ESTÁ LA MAGIA!**
        // Este listener se ejecuta cada vez que el usuario selecciona un ítem.
        spinnerFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val opcionSeleccionada = parent?.getItemAtPosition(position).toString()
                filtrarLista(opcionSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No necesitamos hacer nada aquí
            }
        }
    }

    // 🔥 CAMBIO 4: Nueva función para filtrar la lista
    private fun filtrarLista(estado: String) {
        val listaFiltrada: List<Cuota>

        if (estado == "Mostrar Todas") {
            listaFiltrada = listaCompletaDeCuotas
        } else {
            // Usamos la función filter de Kotlin para crear una nueva lista
            // solo con las cuotas que coinciden con el estado seleccionado.
            listaFiltrada = listaCompletaDeCuotas.filter { cuota ->
                cuota.estado == estado
            }
        }

        // Le decimos al adaptador que actualice su lista y notifique al RecyclerView.
        adapter.actualizarLista(listaFiltrada)
    }
}

