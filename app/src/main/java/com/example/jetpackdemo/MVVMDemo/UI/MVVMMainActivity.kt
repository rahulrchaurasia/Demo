package com.example.jetpackdemo.MVVMDemo.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.jetpackdemo.databinding.ActivityMvvmmainBinding

// For refrence
//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050
class MVVMMainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityMvvmmainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMvvmmainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("MVVM Demo List")

        }

        binding.includeMvvmMain.btnDemo1.setOnClickListener(this)
        binding.includeMvvmMain.btnDemo2.setOnClickListener(this)



    }

    override fun onClick(view: View?) {

        when(view!!.id){

            binding.includeMvvmMain.btnDemo1.id ->
            {
                startActivity(Intent(this, MVVMDemoActivity::class.java))

            }

            binding.includeMvvmMain.btnDemo2.id ->
            {
                startActivity(Intent(this, MVVMGetConstantActivity::class.java))

            }

        }
    }
}