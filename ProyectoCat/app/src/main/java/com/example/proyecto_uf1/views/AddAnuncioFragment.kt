package com.example.proyecto_uf1.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.databinding.FragmentAddAnuncioBinding
import com.example.proyecto_uf1.models.Anuncio
import com.example.proyecto_uf1.network.SupabaseClient
import com.example.proyecto_uf1.viewmodels.AnuncioViewModel
import io.github.jan.supabase.auth.auth


class AddAnuncioFragment : Fragment() {

    private var _binding: FragmentAddAnuncioBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnuncioViewModel by viewModels({ requireActivity() })

    private var anuncioAEditar: Anuncio? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddAnuncioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        anuncioAEditar = AddAnuncioFragmentArgs.fromBundle(requireArguments()).anuncio

        anuncioAEditar?.let {
            binding.etLocalidad.setText(it.localidad)
            binding.etTextoAnuncio.setText(it.texto)
        }

        binding.btnGuardar.setOnClickListener {
            val localidad = binding.etLocalidad.text.toString()
            val texto = binding.etTextoAnuncio.text.toString()
            val userId = SupabaseClient.supabase.auth.currentUserOrNull()?.id

            if (localidad.isNotEmpty() && texto.isNotEmpty() && userId != null) {
                val anuncio = anuncioAEditar?.copy(
                    localidad = localidad,
                    texto = texto
                ) ?: Anuncio(
                    idUsuario = userId,
                    localidad = localidad,
                    texto = texto
                )

                if (anuncioAEditar == null) {
                    viewModel.agregarAnuncio(anuncio)
                } else {
                    viewModel.editarAnuncio(anuncio)
                }

                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}