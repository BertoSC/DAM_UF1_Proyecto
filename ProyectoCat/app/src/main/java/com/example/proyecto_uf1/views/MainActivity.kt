package com.example.proyecto_uf1.views

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyecto_uf1.R
import com.example.proyecto_uf1.databinding.ActivityMainBinding
import com.example.proyecto_uf1.login.LoginActivity
import com.example.proyecto_uf1.network.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de la barra de herramientas y el menú lateral
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val drawerLayout = binding.drawerLayout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        val navigationView = binding.navSide
        navigationView.setupWithNavController(navController)

        // Configuración del correo del usuario logeado en el drawer para testear sesión
        val headerView = navigationView.getHeaderView(0)
        val userEmailText = headerView.findViewById<TextView>(R.id.user_email_text)
        val user = SupabaseClient.supabase.auth.currentUserOrNull()
        if (user != null) {
            Toast.makeText(this, "Usuario: ${user.email}", Toast.LENGTH_SHORT).show()
            println(user)
            userEmailText.text = user.email
        } else {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show()
        }

        // Configuración de la opción Logout en el menú lateral (revisar)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    logout()
                    true
                }
                else -> { // preguntar a Sabela o Pepe por esto
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    binding.drawerLayout.closeDrawers()
                    true
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.container_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.container_fragment)
        NavigationUI.onNavDestinationSelected(item, navController)
        if (item.itemId==R.id.nav_logout){
            logout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        lifecycleScope.launch {
            try {
                SupabaseClient.supabase.auth.signOut()

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al cerrar sesión", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }


}