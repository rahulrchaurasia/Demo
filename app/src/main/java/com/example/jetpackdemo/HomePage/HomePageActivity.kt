package com.example.jetpackdemo.HomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.jetpackdemo.KotlinDemo.KotlinBasicDemoActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomePageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.includeHomepage.btnKotlinBasic.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

        when(view!!.id){

            binding.includeHomepage.btnKotlinBasic.id!! -> {

                startActivity(Intent(this@HomePageActivity, KotlinBasicDemoActivity::class.java))

            }
        }


    }
}