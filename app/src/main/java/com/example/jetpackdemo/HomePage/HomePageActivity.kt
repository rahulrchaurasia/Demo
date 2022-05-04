package com.example.jetpackdemo.HomePage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.example.jetpackdemo.KotlinDemo.KotlinBasicDemoActivity
import com.example.jetpackdemo.ViewModelShareDemo.DemoViewmodelActivity
import com.example.jetpackdemo.WorkManager.WorkManagerDemoActivity
import com.example.jetpackdemo.databinding.ActivityHomePageBinding


class HomePageActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityHomePageBinding
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomePageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        toolbar = supportActionBar!!

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setTitle("Home")
        }


        binding.includeHomepage.btnKotlinBasic.setOnClickListener(this)
        binding.includeHomepage.btnViewModelDemo.setOnClickListener(this)
        binding.includeHomepage.btnWorkManagerDemo.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

        when(view!!.id){

            binding.includeHomepage.btnKotlinBasic.id!! -> {

                startActivity(Intent(this@HomePageActivity, KotlinBasicDemoActivity::class.java))

            }

            binding.includeHomepage.btnViewModelDemo.id!! -> {

                startActivity(Intent(this@HomePageActivity, DemoViewmodelActivity::class.java))


            }

            binding.includeHomepage.btnWorkManagerDemo.id!! -> {

                startActivity(Intent(this@HomePageActivity, WorkManagerDemoActivity::class.java))


            }
        }


    }
}