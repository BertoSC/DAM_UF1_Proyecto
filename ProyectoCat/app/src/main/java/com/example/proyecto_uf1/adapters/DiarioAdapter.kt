package com.example.proyecto_uf1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_uf1.models.DiarioEntry
import com.example.proyecto_uf1.R

// el adaptador vincula los datos de la lista con las vistas del RV, recibe la lista de elementos
// se le pasa una lista y una función para borrar las entradas
// esta funciona como un callback que notifica al fragment o viewmodel cuando se hace clic en el boton de eliminar
//para ejecutar la accion

class DiarioAdapter(private val entradas: List<DiarioEntry>,
                    private val clicEliminar: (DiarioEntry) -> Unit,
                    private val clicEditar: (DiarioEntry) -> Unit
):
    RecyclerView.Adapter<DiarioAdapter.DiarioViewHolder>() {

    // ViewHolder: Enlaza los datos con los componentes visuales> es decir contiene la vista
    // de un único item del RV
    // mantiene una referencia a las vistas de un item especifico, por eso recoge los parámetros

    class DiarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.titulo_diario)
        val texto: TextView = view.findViewById(R.id.texto_diario)
        val fecha: TextView = view.findViewById(R.id.fecha_diario)
        val imagen: ImageView = view.findViewById(R.id.imagen_diario)
    }

    // Lo usa el RV cuando necesita un nuevo VH
    // para eso infla el layout que se diseñó para el item
    // aportando el contexto del RV, la vista padre, infla buscando el ID del item
    // parent se pasa como argumento para que el inflado se asocie al contenedor
    // false se indica para que no se agrege el inflado inmediatamente,
    // puesto que lo hace el RV posteriormente
    // finaliza devolviendo un nuevo Holder al que se le pasa la vist ainflada,
    // para que mantenga las referencias a las vistas del item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiarioViewHolder {
        // Inflar el layout del item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diario, parent, false)
        return DiarioViewHolder(view)
    }

    // vincula los datos con las vistas,
    //se obtiene el objeto en la posición de la lista
    // establee los textos del holder con lso valores del objeto
    // cada vez que el RV necesita mostrar un item llama a esta funcion par anelazar los datos

    override fun onBindViewHolder(holder: DiarioViewHolder, position: Int) {
        holder.itemView.alpha = 0f // Inicia transparente

        // Animación de fade-in con retraso progresivo
        holder.itemView.animate()
            .alpha(1f)
            .setDuration(300L)
            .setStartDelay(100L * position) // Cada item se retrasa 100ms más que el anterior
            .start()

        val entry = entradas[position]
        holder.fecha.text = entry.fecha
        holder.titulo.text = entry.titulo
        holder.texto.text = entry.texto

        if (entry.imagenUri != null) {
            holder.imagen.visibility = View.VISIBLE

            Glide.with(holder.itemView.context)
                .load(entry.imagenUri)
                .into(holder.imagen)

        } else {
            holder.imagen.visibility = View.GONE
        }

        holder.itemView.findViewById<Button>(R.id.btn_eliminar).setOnClickListener {
            clicEliminar(entry)
        }

        holder.itemView.findViewById<Button>(R.id.btn_editar).setOnClickListener {
            clicEditar(entry)
        }
    }

    // devuelve el numero de elementos que hay en la lsita de datos para que el RV sepa
    // cuantos items debe mostrar
    override fun getItemCount(): Int = entradas.size


    // El RecyclerView solicita la cantidad de ítems que necesita mostrar (getItemCount()).
    // Si el RecyclerView necesita mostrar un nuevo ítem, llama a onCreateViewHolder() para crear un nuevo ViewHolder.
    //Luego, llama a onBindViewHolder() para asignar los datos del modelo (título, texto, etc.) a las vistas dentro del ViewHolder.
    //El RecyclerView repite este proceso para todos los ítems que necesita mostrar en la pantalla, reciclando los ViewHolders cuando es necesario.
}