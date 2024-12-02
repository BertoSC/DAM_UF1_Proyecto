package com.example.proyecto_uf1.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.proyecto_uf1.databinding.FragmentEdadBinding
import com.example.proyecto_uf1.viewmodels.DoctorViewModel


class EdadFragment : Fragment() {

    var _binding : FragmentEdadBinding? = null
    val binding get() = _binding!!

    val model: DoctorViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEdadBinding.inflate(inflater, container, false)
        var view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_edad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val consejosTextView = binding.consejosTv
        val imagenGato = binding.gatoEdad
        model.edad.observe(viewLifecycleOwner) { nuevaEdad ->
            consejosTextView.text= getString(model.asignarId())
            imagenGato.setImageResource(model.asignarImg())
        }
    }
}