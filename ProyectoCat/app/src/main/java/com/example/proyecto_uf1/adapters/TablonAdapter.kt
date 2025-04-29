package com.example.proyecto_uf1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.models.Anuncio

class TablonAdapter (private val anuncios: List<Anuncio>,
                     private val userIdActual: String?,
                     private val clicEliminar: (Anuncio) -> Unit,
                     private val clicEditar: (Anuncio) -> Unit
):
    RecyclerView.Adapter<TablonAdapter.TablonViewHolder>(){

    class TablonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val localidad: TextView = view.findViewById(R.id.localizacion_anuncio)
        val texto: TextView = view.findViewById(R.id.texto_anuncio)
        val btnEditar: Button = view.findViewById(R.id.btn_editar_anuncio)
        val btnEliminar: Button = view.findViewById(R.id.btn_eliminar_anuncio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anuncio, parent, false)
        return TablonViewHolder(view)
    }

    override fun onBindViewHolder(holder: TablonViewHolder, position: Int){
        val anuncio = anuncios[position]
        holder.localidad.text = anuncio.localidad
        holder.texto.text = anuncio.texto
        if (anuncio.idUsuario == userIdActual) {
            holder.btnEditar.visibility = View.VISIBLE
            holder.btnEliminar.visibility = View.VISIBLE
        } else {
            holder.btnEditar.visibility = View.GONE
            holder.btnEliminar.visibility = View.GONE
        }

        holder.itemView.findViewById<Button>(R.id.btn_eliminar_anuncio).setOnClickListener{
            clicEliminar(anuncio)
        }

        holder.itemView.findViewById<Button>(R.id.btn_editar_anuncio).setOnClickListener {
            clicEditar(anuncio)
        }
    }

    override fun getItemCount(): Int = anuncios.size




}