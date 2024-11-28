import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_uf1.CatApiService
import com.example.proyecto_uf1.CatImageResponse
import com.example.proyecto_uf1.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomCatViewModel : ViewModel() {

    private val _catImageUrl = MutableLiveData<String?>()
    val catImageUrl: LiveData<String?> get() = _catImageUrl

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun obtenerGatito() {
        val catApi = RetrofitClient.instance.create(CatApiService::class.java)
        catApi.getRandomCatImage().enqueue(object : Callback<List<CatImageResponse>> {
            override fun onResponse(
                call: Call<List<CatImageResponse>>,
                response: Response<List<CatImageResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val catImageUrl = response.body()!![0].url
                    _catImageUrl.value = catImageUrl
                } else {
                    _error.value = "ERROR"
                }
            }

            override fun onFailure(call: Call<List<CatImageResponse>>, t: Throwable) {
                t.printStackTrace()
                _error.value = "NETWORK ERROR: ${t.message}"
            }
        })
    }
}
