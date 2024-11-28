package com.example.proyecto_uf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomCatFragment : Fragment() {

    private lateinit var catImageView: ImageView
    private lateinit var fetchButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_cat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catImageView = view.findViewById(R.id.imagenRandom)
        fetchButton = view.findViewById(R.id.btn_random)

        fetchButton.setOnClickListener {
            obtenerGatito()
        }
    }

    private fun obtenerGatito() {
        val catApi = RetrofitClient.instance.create(CatApiService::class.java)
        catApi.getRandomCatImage().enqueue(object : Callback<List<CatImageResponse>> {
            override fun onResponse(
                call: Call<List<CatImageResponse>>,
                response: Response<List<CatImageResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val catImageUrl = response.body()!![0].url
                    Glide.with(this@RandomCatFragment)
                        .load(catImageUrl)
                        .into(catImageView)
                }
            }

            override fun onFailure(call: Call<List<CatImageResponse>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(requireContext(), getText(R.string.errorAPI), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
