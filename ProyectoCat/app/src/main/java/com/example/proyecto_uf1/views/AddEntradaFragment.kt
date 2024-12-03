package com.example.proyecto_uf1.views
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.databinding.FragmentAddEntradaBinding
import com.example.proyecto_uf1.models.DiarioEntry
import com.example.proyecto_uf1.viewmodels.DiarioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddEntradaFragment : Fragment() {

    var _binding: FragmentAddEntradaBinding? = null;
    val binding get() = _binding!!

    val model: DiarioViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    // clase para iniciar una actividad y obtener un resultado
    // se registra el lanzador, en este caso de imagenes
    // se lanza la actividad (con el intent)
    // intent es un objeto para comunicarse entre componentes del SO

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEntradaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_add_entrada, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        // Inicializar el ActivityResultLauncher
        // usuario selecciona una imagen de la galería,
        // el resultado de esa acción (la URI de la imagen seleccionada)
        // es devuelto a nuestro fragmento.
        // como el intend devuelve un uri temporal, copiamos la imagen usando una función del model
        // y asignamos esta neuva ubicación al ImageView

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data?.data
                val savedImagePath = selectedImageUri?.let { model.guardarImagenEnAlmacenamiento(it) }
                savedImagePath?.let {
                    selectedImageUri = Uri.parse(it) // Actualiza la URI con la ruta persistente
                }

                val imageView: ImageView =binding.imageView
                imageView.setImageURI(selectedImageUri)
            }
        }

        // Configurar el botón para seleccionar la imagen
        //Este intent le dice al sistema operativo que queremos seleccionar algo
        // (en este caso, una imagen) de la galería del dispositivo.
        // en concreto, a las imagenes de la memoria

        val selectImageButton: Button = view.findViewById(R.id.btn_seleccionar_imagen)
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        }

        val etTitulo: EditText = binding.etTitulo
        val etTexto: EditText = binding.etTexto
        val btnGuardar: Button = binding.btnGuardar

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val texto = etTexto.text.toString()

            if (titulo.isNotEmpty() && texto.isNotEmpty()) {
                val imagenUriString = selectedImageUri?.toString()
                val nuevaEntrada = DiarioEntry(titulo, texto, currentDate, imagenUriString)
                model.agregarEntrada(nuevaEntrada)
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), getText(R.string.toastentrada), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}