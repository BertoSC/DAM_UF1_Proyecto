package com.example.proyecto_uf1.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.adapters.IconoGatoAdapter
import com.example.proyecto_uf1.databinding.FragmentPerfilBinding
import com.example.proyecto_uf1.models.Mascota
import com.example.proyecto_uf1.viewmodels.MascotaViewModel

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MascotaViewModel by viewModels()
    private val listaMascotas = mutableListOf<Mascota>()

    private lateinit var adapter: IconoGatoAdapter
    private var mascotaSeleccionada: Mascota? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = IconoGatoAdapter(listaMascotas) { mascota ->
            mostrarDetalleMascota(mascota)
        }

        binding.rvGatos.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvGatos.adapter = adapter


        viewModel.mascotas.observe(viewLifecycleOwner) { lista ->
            listaMascotas.clear()
            listaMascotas.addAll(lista)
            adapter.notifyDataSetChanged()

            if (lista.isNotEmpty()) {
                mostrarDetalleMascota(lista.first())
            }
        }

        binding.btnAnadirGato.setOnClickListener {
            findNavController().navigate(R.id.action_perfilFragment_to_addPerfilFragment)
        }
    }

    private fun mostrarDetalleMascota(mascota: Mascota) {
        mascotaSeleccionada = mascota
        binding.gatitoNombre.text = mascota.nombre
        binding.gatitoEdad.text = mascota.edad?.toString() ?: "-"
        binding.gatitoRaza.text = mascota.raza ?: "-"
        binding.gatitoAlim.text = mascota.alimentacion ?: "-"
        binding.gatitoMed.text = mascota.medicacion ?: "-"


        Glide.with(this)
            .load(mascota.imagenGatoUrl)
            .centerCrop()
            .into(binding.imagenPerfil)
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarMascotas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}