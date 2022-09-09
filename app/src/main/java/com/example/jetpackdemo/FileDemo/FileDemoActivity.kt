package com.example.jetpackdemo.FileDemo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.jetpackdemo.R

import com.example.jetpackdemo.databinding.ActivityFileDemoBinding

class FileDemoActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFileDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFileDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

//        supportActionBar!!.apply {
//
//            setDisplayShowHomeEnabled(true)
//            setDisplayHomeAsUpEnabled(true)
//
//        }


        val navController = findNavController(R.id.nav_host_fragment_content_file_demo)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_file_demo)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}