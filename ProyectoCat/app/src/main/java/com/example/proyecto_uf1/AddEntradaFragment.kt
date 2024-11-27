package com.example.proyecto_uf1
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddEntradaFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_add_entrada, container, false)
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

                val imageView: ImageView = view.findViewById(R.id.imageView)
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


        val etTitulo: EditText = view.findViewById(R.id.et_titulo)
        val etTexto: EditText = view.findViewById(R.id.et_texto)
        val btnGuardar: Button = view.findViewById(R.id.btn_guardar)


        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val texto = etTexto.text.toString()

            if (titulo.isNotEmpty() && texto.isNotEmpty()) {
                val imagenUriString = selectedImageUri?.toString()
                val nuevaEntrada = DiarioEntry(titulo, texto, currentDate, imagenUriString)
                model.agregarEntrada(nuevaEntrada)
                findNavController().popBackStack()
                // se elimina el frag actual de la pila y regresa al anterior
            }
        }
    }

    /*fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(requireContext().filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            file.absolutePath // Devuelve la ruta completa donde se guardó la imagen
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }*/
}