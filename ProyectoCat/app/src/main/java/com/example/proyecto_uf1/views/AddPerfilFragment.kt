package com.example.proyecto_uf1.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.jan.supabase.auth.auth
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.proyecto_uf1.databinding.FragmentAddPerfilBinding
import com.example.proyecto_uf1.models.Mascota
import com.example.proyecto_uf1.network.SupabaseClient
import com.example.proyecto_uf1.viewmodels.MascotaViewModel
import kotlinx.coroutines.launch

class AddPerfilFragment : Fragment() {

    private var _binding: FragmentAddPerfilBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MascotaViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    Glide.with(this)
                        .load(it)
                        .into(binding.imageView)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSeleccionarImagenPerfil.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        binding.btnGuardarPerfil.setOnClickListener {
            guardarPerfil()
        }
    }

    private fun guardarPerfil() {
        val nombre = binding.etIntroduceNombre.text.toString().trim()
        val edad = binding.etIntroduceEdad.text.toString().toIntOrNull()
        val raza = binding.etIntroduceRaza.text.toString().trim()
        val alimentacion = binding.etAlimentacion.text.toString().trim()
        val medicacion = binding.etMedicacion.text.toString().trim()
        val vacunas = binding.etVacunas.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(requireContext(), "Introduce al menos el nombre", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = SupabaseClient.supabase.auth.currentUserOrNull()?.id
        if (userId == null) {
            Toast.makeText(requireContext(), "Usuario no identificado", Toast.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val imageUrl = selectedImageUri?.let {
                viewModel.subirImagen(it)
            } ?: ""

            val nuevaMascota = Mascota(
                idUsuario = userId,
                nombre = nombre,
                edad = edad,
                raza = raza.ifEmpty { null },
                vacunas = vacunas.ifEmpty { null },
                alimentacion = alimentacion.ifEmpty { null },
                medicacion = medicacion.ifEmpty { null },
                imagenGatoUrl = imageUrl
            )

            viewModel.agregarMascota(nuevaMascota)
            Toast.makeText(requireContext(), "Perfil guardado", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
