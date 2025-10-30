package com.example.clubdeportivobola8

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivobola8.data.DBHelper
import com.example.clubdeportivobola8.data.model.Socio
import com.example.clubdeportivobola8.databinding.ActivityListadoSociosBinding

// 1. La clase DEBE heredar de AppCompatActivity
class ListadoSociosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListadoSociosBinding
    private lateinit var adapter: SocioAdapter
    private lateinit var dbHelper: DBHelper

    // 3. Todo el código debe estar DENTRO de la clase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla el layout usando la clase de enlace
        binding = ActivityListadoSociosBinding.inflate(layoutInflater)
        // Establece la vista raíz del enlace como el contenido de la actividad
        setContentView(binding.root)

        // --- Inicializar DBHelper ---
        dbHelper = DBHelper.getInstance(this)

        // --- Configurar RecyclerView usando 'binding' ---
        // Ahora accedes a las vistas de forma segura
        binding.rvListadoSocios.layoutManager = LinearLayoutManager(this)

        // Obtenemos la lista de socios y la pasamos al nuevo SocioAdapter
        val listaSocios = dbHelper.obtenerSocios()
        adapter = SocioAdapter(listaSocios)
        binding.rvListadoSocios.adapter = adapter

        // --- Configurar el Spinner (la lógica de filtrado está pendiente) ---
        //setupSpinner()

        // --- Lógica del botón para volver atrás usando 'binding' ---
        binding.btnVolverMenu.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la anterior
        }
    }

    private fun setupSpinner() {
        val opcionesFiltro = arrayOf("Mostrar Todos", "Socios Activos", "Socios Inactivos")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesFiltro)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Accede al spinner de forma segura a través de 'binding'
        binding.spFiltroSocios.adapter = spinnerAdapter
    }
}
