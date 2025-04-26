package com.example.proyecto_uf1.views
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.databinding.FragmentDiarioBinding
import com.example.proyecto_uf1.models.DiarioEntry
import com.example.proyecto_uf1.viewmodels.DiarioViewModel
import com.google.android.material.imageview.ShapeableImageView
import java.util.Locale

class DiarioFragment : Fragment() {

    var _binding: FragmentDiarioBinding?=null
    val binding get() = _binding!!

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
        _binding = FragmentDiarioBinding.inflate(inflater, container, false)
        var view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_diario, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        diarioEntries = mutableListOf()
        adapter = DiarioAdapter(diarioEntries,
            clicEliminar = { entryToDelete ->
                model.eliminarEntrada(entryToDelete)
            },
            clicEditar = { entryToEdit ->
                val action = DiarioFragmentDirections.actionDiarioFragmentToAddEntradaFragment(entryToEdit)
                findNavController().navigate(action)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        // Observar cambios en las entradas del ViewModel, la propiedad de solo observacion
        // el objeto viewlifecicleowner permite que solo se observen cambios cuando la vista esta activa
        // si hay cambios, se entra al código de la lambda
        //La lista diarioEntries es la que está vinculada al RecyclerView a través del adaptador.
        // Este código limpia la lista de entradas actual en diarioEntries,
        // eliminando todos los elementos anteriores. >> para tener la lista actualizada siempre


        model.entradas.observe(viewLifecycleOwner) { lista ->
            diarioEntries.clear()

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val listaOrdenada = lista.sortedByDescending { entrada ->
                dateFormat.parse(entrada.fecha)
            }

            diarioEntries.addAll(listaOrdenada)
            adapter.notifyDataSetChanged()
        }


        val myFabImage: ShapeableImageView = view.findViewById(R.id.my_fab_image)
        myFabImage.setOnClickListener {
            val action = DiarioFragmentDirections.actionDiarioFragmentToAddEntradaFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}