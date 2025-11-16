package com.example.clubdeportivobola8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CuotaAdapter(private var listaCuotas: List<Cuota>) :
    RecyclerView.Adapter<CuotaAdapter.CuotaViewHolder>()
{

    fun actualizarLista(nuevaLista: List<Cuota>) {
        listaCuotas = nuevaLista
        notifyDataSetChanged()
    }

    inner class CuotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSocio: TextView = itemView.findViewById(R.id.tvSocioNombre)
        val tvActividad: TextView = itemView.findViewById(R.id.tvActividad)
        val tvVencimiento: TextView = itemView.findViewById(R.id.tvVencimiento)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstadoCuota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cuota, parent, false)
        return CuotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuotaViewHolder, position: Int) {
        val cuota = listaCuotas[position]
        holder.tvSocio.text = cuota.nombreSocio
        holder.tvActividad.text = "Actividad: ${cuota.actividad}"
        holder.tvVencimiento.text = "Vence: ${cuota.fechaVencimiento}"
        holder.tvEstado.text = "Estado: ${cuota.estado}"

        // --- Cambiar color según estado ---
        val colorRes = when (cuota.estado) {
            "Vencida" -> android.R.color.holo_red_dark
            "Pagada" -> android.R.color.holo_green_dark
            "Por vencer" -> android.R.color.holo_orange_dark
            else -> android.R.color.black
        }
        holder.tvEstado.setTextColor(holder.itemView.resources.getColor(colorRes))
    }

    override fun getItemCount(): Int = listaCuotas.size
}
