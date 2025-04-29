package com.example.proyecto_uf1.views

import io.github.jan.supabase.auth.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_uf1.adapters.TablonAdapter
import com.example.proyecto_uf1.databinding.FragmentTablonBinding
import com.example.proyecto_uf1.models.Anuncio
import com.example.proyecto_uf1.network.SupabaseClient
import com.example.proyecto_uf1.viewmodels.AnuncioViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView

class TablonFragment : Fragment() {

    private var _binding: FragmentTablonBinding? = null
    private val binding get() = _binding!!

    private val model: AnuncioViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private lateinit var adapter: TablonAdapter
    private val listaOriginal = mutableListOf<Anuncio>()
    private val listaVisible = mutableListOf<Anuncio>()

    private var viendoSoloPropios = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTablonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = SupabaseClient.supabase.auth.currentUserOrNull()?.id

        adapter = TablonAdapter(listaVisible, userId,
            clicEliminar = { anuncio -> model.eliminarAnuncio(anuncio) },
            clicEditar = { anuncio ->
                //val action = TablonFragmentDirections.actionTablonFragmentToAddAnuncioFragment(anuncio)
                //findNavController().navigate(action)
            }
        )

        binding.recyclerViewAnuncios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAnuncios.adapter = adapter

        model.anuncios.observe(viewLifecycleOwner) { anuncios ->
            listaOriginal.clear()
            listaOriginal.addAll(anuncios)
            aplicarFiltro(binding.etBuscarAnuncio.text.toString())
        }

        binding.etBuscarAnuncio.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                aplicarFiltro(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnTusAnuncios.setOnClickListener {
            viendoSoloPropios = !viendoSoloPropios
            aplicarFiltro(binding.etBuscarAnuncio.text.toString())
        }

        binding.iconoAnadirAnuncio.setOnClickListener {
            //val action = TablonFragmentDirections.actionTablonFragmentToAddAnuncioFragment(null)
            //findNavController().navigate(action)
        }

        model.cargarAnuncios()
    }

    private fun aplicarFiltro(query: String) {
        val userId = SupabaseClient.supabase.auth.currentUserOrNull()?.id

        val listaFiltrada = listaOriginal.filter { anuncio ->
            val coincideConFiltro = query.isEmpty() || anuncio.localidad.contains(query, ignoreCase = true)
            val esDelUsuario = !viendoSoloPropios || anuncio.idUsuario == userId
            coincideConFiltro && esDelUsuario
        }

        listaVisible.clear()
        listaVisible.addAll(listaFiltrada)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
