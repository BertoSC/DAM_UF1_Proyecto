package com.example.proyecto_uf1.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.models.Mascota
import com.google.android.material.imageview.ShapeableImageView

class IconoGatoAdapter(
    private val mascotas: MutableList<Mascota>,
    private val onClick: (Mascota) -> Unit
) : RecyclerView.Adapter<IconoGatoAdapter.IconoGatoViewHolder>() {

    class IconoGatoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconoGato: ShapeableImageView = view.findViewById(R.id.ivIconoGato)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconoGatoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gato_icono, parent, false)
        return IconoGatoViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconoGatoViewHolder, position: Int) {
        val mascota = mascotas[position]

        Glide.with(holder.itemView.context)
            .load(mascota.imagenGatoUrl)
            .into(holder.iconoGato)

        holder.itemView.setOnClickListener {
            onClick(mascota)
        }
    }

    override fun getItemCount(): Int = mascotas.size
}