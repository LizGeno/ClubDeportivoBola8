package com.example.clubdeportivobola8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivobola8.data.model.Socio

class SocioAdapter(private val socios: List<Socio>) : RecyclerView.Adapter<SocioAdapter.SocioViewHolder>() {

    // ViewHolder: Contiene las referencias a las vistas (TextViews) de cada fila.
    class SocioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreCompleto: TextView = itemView.findViewById(R.id.tvNombreCompleto)
        val dni: TextView = itemView.findViewById(R.id.tvDni)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
    }

    // Crea una nueva vista (fila) para el RecyclerView.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_socio, parent, false)
        return SocioViewHolder(view)
    }

    // Vincula los datos de un Socio con las vistas de un ViewHolder.
    override fun onBindViewHolder(holder: SocioViewHolder, position: Int) {
        val socio = socios[position]
        holder.nombreCompleto.text = "${socio.nombre} ${socio.apellido}"
        holder.dni.text = "DNI: ${socio.dni}"
        holder.email.text = socio.email
    }

    // Devuelve el número total de elementos en la lista.
    override fun getItemCount(): Int {
        return socios.size
    }
}
    