package com.example.clubdeportivobola8

import android.os.Bundle
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button
import com.example.clubdeportivobola8.MenuPrincipalActivity



class ListadoCuotasActivity : AppCompatActivity() {

    // 🔹 Declarar variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CuotaAdapter
    private lateinit var spinnerFiltro: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_cuotas)

        // 🔹 Si tu layout tiene un contenedor raíz con id "main"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Vincular vistas
        recyclerView = findViewById(R.id.rvListadoCuotas)
        spinnerFiltro = findViewById(R.id.spFiltroCuotas)

        // 🔹 Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 🔹 Datos de ejemplo
        val listaCuotas = listOf(
            Cuota("Juan Pérez", "Fútbol", "10/10/2025", "Vencida"),
            Cuota("María López", "Tenis", "15/10/2025", "Por vencer"),
            Cuota("Carlos Gómez", "Natación", "25/09/2025", "Vencida")
        )

        // 🔹 Configurar Adapter
        adapter = CuotaAdapter(listaCuotas)
        recyclerView.adapter = adapter
        // 🔹 Botón para volver atrás
        val btnBack = findViewById<Button>(R.id.btnVolverMenu)
        btnBack.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
    }


}

}
