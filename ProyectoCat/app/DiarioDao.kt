import androidx.room.*

@Dao
interface DiarioDao {
    @Query("SELECT * FROM diario_entries")
    fun getEntradas(): List<DiarioEntry>

    @Insert
    suspend fun insertarEntrada(entry: DiarioEntry)

    @Delete
    suspend fun eliminarEntrada(entry: DiarioEntry)
}