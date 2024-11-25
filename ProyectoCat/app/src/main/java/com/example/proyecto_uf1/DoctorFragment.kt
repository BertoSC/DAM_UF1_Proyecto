package com.example.proyecto_uf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class DoctorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                    val action = DoctorFragmentDirections.actionDoctorFragmentToEdadFragment(edad)
                    findNavController().navigate(action)
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), getText(R.string.toastFormatoNum), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), getText(R.string.toastCampoVacio), Toast.LENGTH_SHORT).show()
            }
        }
        }
    }

