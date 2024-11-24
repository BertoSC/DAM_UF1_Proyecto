package com.example.proyecto_uf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class EdadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val consejosTextView = view.findViewById<TextView>(R.id.consejos_tv)
        val imagenGato = view.findViewById<ImageView>(R.id.gato_edad)
        val args = EdadFragmentArgs.fromBundle(requireArguments())
        val edadGato = args.edad

        // Muestra consejos según la edad
        val consejos = when {
            edadGato < 1 -> getString(R.string.cachorro)
            edadGato in 1..3 -> getString(R.string.joven)
            edadGato in 4..11 ->getString(R.string.adulto)
            else -> getString(R.string.senior)
        }

        when {
            edadGato < 1 -> imagenGato.setImageResource(R.drawable.cachorro)
            edadGato in 1..3 -> imagenGato.setImageResource(R.drawable.joven)
            edadGato in 4..11 -> imagenGato.setImageResource(R.drawable.adulto)
            else ->  imagenGato.setImageResource(R.drawable.senior)
        }

        consejosTextView.text = consejos
    }
}