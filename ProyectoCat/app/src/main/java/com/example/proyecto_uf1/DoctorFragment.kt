package com.example.proyecto_uf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController


class DoctorFragment : Fragment() {

    val model: DoctorViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edadEditText = view.findViewById<EditText>(R.id.edad_tv)
        val verConsejosButton = view.findViewById<Button>(R.id.btn_edad)

        verConsejosButton.setOnClickListener {
            val edadTexto = edadEditText.text.toString()
            if (edadTexto.isNotEmpty()) {
                try {
                    val edad = edadTexto.toInt()
                    model.setEdad(edad)
                    //val action = DoctorFragmentDirections.actionDoctorFragmentToEdadFragment(edad)
                    //findNavController().navigate(action)
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

