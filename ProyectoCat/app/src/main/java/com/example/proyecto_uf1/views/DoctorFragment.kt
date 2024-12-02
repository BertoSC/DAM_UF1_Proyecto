package com.example.proyecto_uf1.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyecto_uf1.viewmodels.DoctorViewModel
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.databinding.FragmentDoctorBinding


class DoctorFragment : Fragment() {
    var _binding : FragmentDoctorBinding? = null
    val binding get() = _binding!!

    val model: DoctorViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)
        val view =binding.root
        return view
        //return inflater.inflate(R.layout.fragment_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edadEditText = binding.edadTv
        val verConsejosButton = binding.btnEdad

        verConsejosButton.setOnClickListener {
            val edadTexto = edadEditText.text.toString()
            if (edadTexto.isNotEmpty()) {
                try {
                    val edad = edadTexto.toInt()
                    model.setEdad(edad)
                    findNavController().navigate(R.id.action_doctorFragment_to_edadFragment)
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), getText(R.string.toastFormatoNum), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), getText(R.string.toastCampoVacio), Toast.LENGTH_SHORT).show()
            }
        }
    }
}