package com.example.clubdeportivobola8

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clubdeportivobola8.data.DBHelper
import com.example.clubdeportivobola8.data.model.Socio
import com.example.clubdeportivobola8.databinding.ActivityListadoSociosBinding


class ListadoSociosActivity : AppCompatActivity(), SocioClickListener {
    private lateinit var binding: ActivityListadoSociosBinding
    private lateinit var adapter: SocioAdapter
    private lateinit var dbHelper: DBHelper
    // La lista ahora se pasa directamente al adaptador, no necesitamos una propiedad de clase para ella.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoSociosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper.getInstance(this)
        setupRecyclerView() // Llama a la configuración del RecyclerView

        binding.btnVolverMenu.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // Cada vez que volvemos a esta pantalla (p.ej. después de editar),
        // recargamos los datos para ver los cambios.
        loadSocios()
    }


    private fun setupRecyclerView() {
        binding.rvListadoSocios.layoutManager = LinearLayoutManager(this)
        adapter = SocioAdapter(emptyList(), this)
        binding.rvListadoSocios.adapter = adapter
    }

    private fun loadSocios() {
        // Obtiene los socios de la BD
        val sociosFromDb = dbHelper.obtenerSocios()
        // Actualiza los datos en el adaptador existente
        adapter.updateData(sociosFromDb)
    }

    // --- 2. Implementación de los métodos de la interfaz ---

    override fun onEditSocio(socio: Socio) {
        // Esta lógica ahora se ejecutará porque el adaptador ya sabe a quién llamar.
        val intent = Intent(this, AltaSocioActivity::class.java).apply {
            putExtra("SOCIO_ID", socio.id)
        }
        startActivity(intent)
    }

    override fun onDeleteSocio(socio: Socio) {
        // Muestra un diálogo de confirmación para eliminar
        AlertDialog.Builder(this)
            .setTitle("Confirmar Eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar a ${socio.nombre} ${socio.apellido}?")
            .setPositiveButton("Sí, Eliminar") { _, _ ->
                val exito = dbHelper.deleteSocio(socio.id)
                if (exito) {
                    Toast.makeText(this, "Socio eliminado correctamente", Toast.LENGTH_SHORT).show()
                    loadSocios() // Recarga la lista para refrescar la UI
                } else {
                    Toast.makeText(this, "Error al eliminar el socio", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
