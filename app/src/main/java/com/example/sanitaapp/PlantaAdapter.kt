package com.example.sanitaapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sanitaapp.R
import com.example.sanitaapp.model.Planta

class PlantaAdapter(private val lista: List<Planta>) : RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder>() {

    class PlantaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreText: TextView = itemView.findViewById(R.id.plantaNombre)
        val imagenView: ImageView = itemView.findViewById(R.id.plantaImagen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.planta_item, parent, false)
        return PlantaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
        val planta = lista[position]
        holder.nombreText.text = planta.nombre

        Glide.with(holder.itemView.context)
            .load(planta.imagen)
            .into(holder.imagenView)
    }

    override fun getItemCount(): Int = lista.size
}