package com.example.proyecto_uf1.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.databinding.FragmentRegisterBinding
import com.example.proyecto_uf1.network.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registerButton.setOnClickListener {
            val mail = binding.emailEditText.text.toString().trim()
            val pass= binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
            val acceptedPrivacy = binding.privacyPolicyCheckbox.isChecked


            // Configuración de validaciones de campos
            if (mail.isEmpty() || pass.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPassword) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!acceptedPrivacy) {
                Toast.makeText(requireContext(), "Debes aceptar la política de privacidad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Registro de usuario usando supabase
            lifecycleScope.launch {
                try {
                    SupabaseClient.supabase.auth.signUpWith(Email) {
                        email = mail
                        password= pass
                    }

                    Toast.makeText(requireContext(), "Registro exitoso. Inicia sesión.", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                } catch (e: RestException) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error inesperado", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }

        binding.privacyPolicyLink.setOnClickListener {
            Toast.makeText(requireContext(), "Mostrar política de privacidad", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


