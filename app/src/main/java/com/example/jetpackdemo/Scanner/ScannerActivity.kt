package com.example.jetpackdemo.Scanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.KotlinDemo.KotlinBasicDemoActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityScannerBinding



class ScannerActivity : BaseActivity() {

    lateinit var binding : ActivityScannerBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayUseLogoEnabled(true)

        }

        theme.applyStyle(R.style.FullScreen,false)


       binding.includeScanner.btnScanner1.setOnClickListener{

           startActivity(Intent(this, ScannerDemoActivity::class.java))
       }

        binding.includeScanner.btnScanner2.setOnClickListener{

            startActivity(Intent(this, TextScannerActivity::class.java))
        }
    }




}