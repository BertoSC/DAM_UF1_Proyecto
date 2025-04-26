package com.example.proyecto_uf1.views

import com.example.proyecto_uf1.viewmodels.RandomCatViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.proyecto_uf1.databinding.FragmentRandomCatBinding

class RandomCatFragment : Fragment() {

    var _binding: FragmentRandomCatBinding? = null
    val binding get() = _binding!!

    private lateinit var catImageView: ImageView
    private lateinit var fetchButton: Button
    private lateinit var breedInfoTextView: TextView

    private val viewModel: RandomCatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catImageView = binding.imagenRandom
        fetchButton = binding.btnRandom
        breedInfoTextView = binding.catBreedInfo

        viewModel.catImageUrl.observe(viewLifecycleOwner) { url ->
            url?.let {
                Glide.with(this).load(it).into(catImageView)
            }
        }

        viewModel.catBreedInfo.observe(viewLifecycleOwner) { breedInfo ->
            breedInfo?.let {
                breedInfoTextView.text = it  // Mostrar la información de la raza
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        fetchButton.setOnClickListener {
            viewModel.obtenerGatito()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*
class RandomCatFragment : Fragment() {

    var _binding: FragmentRandomCatBinding?=null
    val binding get() = _binding!!

    private lateinit var catImageView: ImageView
    private lateinit var fetchButton: Button

    private val viewModel: RandomCatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomCatBinding.inflate(inflater, container, false)
        var view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_random_cat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catImageView = binding.imagenRandom
        fetchButton = binding.btnRandom

        viewModel.catImageUrl.observe(viewLifecycleOwner) { url ->
            url?.let {
                Glide.with(this).load(it).into(catImageView)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        fetchButton.setOnClickListener {
            viewModel.obtenerGatito()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
*/