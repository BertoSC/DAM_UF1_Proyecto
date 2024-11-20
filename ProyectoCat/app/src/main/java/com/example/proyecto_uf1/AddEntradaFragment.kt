package com.example.proyecto_uf1
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class AddEntradaFragment : Fragment() {

    val model: DiarioViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_entrada, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)

       val etTitulo: EditText = view.findViewById(R.id.et_titulo)
       val etTexto: EditText = view.findViewById(R.id.et_texto)
       val btnGuardar: Button = view.findViewById(R.id.btn_guardar)

            btnGuardar.setOnClickListener {
                val titulo = etTitulo.text.toString()
                val texto = etTexto.text.toString()

                if (titulo.isNotEmpty() && texto.isNotEmpty()) {
                    val nuevaEntrada = DiarioEntry(titulo, texto)
                   model.agregarEntrada(nuevaEntrada)
                    findNavController().popBackStack()
                // se elimina el frag actual de la pila y regresa al anterior
                }
            }
        }
}