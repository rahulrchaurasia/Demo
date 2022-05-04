package com.example.jetpackdemo.ViewModelShareDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.ActivityDemoViewmodelBinding

class DemoViewmodelActivity : AppCompatActivity() {

    lateinit var binding : ActivityDemoViewmodelBinding
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoViewmodelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        toolbar = supportActionBar!!

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("ViewModel Demo")
        }
    }
}