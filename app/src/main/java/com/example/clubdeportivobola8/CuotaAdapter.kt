package com.example.clubdeportivobola8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CuotaAdapter(private var listaCuotas: List<Cuota>) :
    RecyclerView.Adapter<CuotaAdapter.CuotaViewHolder>()
{// Dentro de la clase CuotaAdapter

    fun actualizarLista(nuevaLista: List<Cuota>) {
        listaCuotas = nuevaLista
        notifyDataSetChanged() // Este comando le dice al RecyclerView: "¡Hey, la lista cambió, redibújate!"
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

        // Cambiar color según estado
        holder.tvEstado.setTextColor(
            if (cuota.estado == "Vencida")
                holder.itemView.resources.getColor(android.R.color.holo_red_dark)
            else
                holder.itemView.resources.getColor(android.R.color.holo_green_dark)
        )
    }

    override fun getItemCount(): Int = listaCuotas.size
}
