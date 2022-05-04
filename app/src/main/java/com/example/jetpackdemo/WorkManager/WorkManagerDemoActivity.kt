package com.example.jetpackdemo.WorkManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.ActivityWorkManagerDemoBinding

class WorkManagerDemoActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
    private lateinit var binding: ActivityWorkManagerDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkManagerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        toolbar = supportActionBar!!

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            //setTitle("Work Manger Demo")
        }



    }
}