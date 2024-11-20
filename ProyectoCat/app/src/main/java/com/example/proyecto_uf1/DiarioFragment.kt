package com.example.proyecto_uf1

import DiarioAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class DiarioFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DiarioAdapter
    private lateinit var diarioEntries: MutableList<DiarioEntry>

    val model: DiarioViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        diarioEntries = mutableListOf()
        adapter = DiarioAdapter(diarioEntries)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observar cambios en las entradas del ViewModel, la propiedad de solo observacion
        // el objeto viewlifecicleowner permite que solo se observen cambios cuando la vista esta activa
        // si hay cambios, se entra al código de la lambda
        //La lista diarioEntries es la que está vinculada al RecyclerView a través del adaptador.
        // Este código limpia la lista de entradas actual en diarioEntries,
        // eliminando todos los elementos anteriores. >> para tener la lista actualizada siempre


        model.entradas.observe(viewLifecycleOwner) { lista ->
            diarioEntries.clear()  // limpia la lista actual
            diarioEntries.addAll(lista)  // una vez limpiada, se agrega la version nueva desde el ViewModel
            adapter.notifyDataSetChanged() // se notifican los cambios al adaptador del RV
        }

        // Configurar FAB para navegar al fragmento de añadir entrada
        val myFabImage: ShapeableImageView = view.findViewById(R.id.my_fab_image)
        myFabImage.setOnClickListener {
            val action = DiarioFragmentDirections.actionDiarioFragmentToAddEntradaFragment()
            findNavController().navigate(action)
        }
    }
}