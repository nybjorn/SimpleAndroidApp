package com.example.simpleandroidapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.simpleandroidapp.service.NoLimitBeerService
import com.example.simpleandroidapp.service.ServiceAction
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navController = findNavController(R.id.main_nav_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        setupBottomNavMenu(navController)

        startService()
    }

    private fun startService() {
        Intent(this, NoLimitBeerService::class.java).also {
            it.action = ServiceAction.START.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Timber.d("Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            Timber.d("Starting the service in < 26 Mode")
            startService(it)
        }
    }

    private fun setupBottomNavMenu(findNavController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(findNavController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}
