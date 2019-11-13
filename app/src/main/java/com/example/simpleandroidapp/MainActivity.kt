package com.example.simpleandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.facebook.stetho.Stetho
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this)
        setContentView(R.layout.main_activity)

        navController = findNavController(R.id.main_nav_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(findNavController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(findNavController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}
