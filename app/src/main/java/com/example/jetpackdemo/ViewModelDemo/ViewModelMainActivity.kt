package com.example.jetpackdemo.ViewModelDemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.KotlinDemo.KotlinBasicDemoActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ViewModelDemo.LiveDataDemo.LiveDataDemoActivity
import com.example.jetpackdemo.ViewModelDemo.ViewModelFactoryDemo.ViewModelDemo3
import com.example.jetpackdemo.ViewModelDemo.ViewModelFactoryDemo.ViewModelDemo3Activity
import com.example.jetpackdemo.ViewModelShareDemo.DemoViewmodelActivity
import com.example.jetpackdemo.databinding.ActivityViewModelMainBinding

class ViewModelMainActivity : BaseActivity() ,View.OnClickListener {

    lateinit var binding : ActivityViewModelMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewModelMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("ViewModel Demo")
        }

        binding.btnDemo1.setOnClickListener(this)
        binding.btnDemo2.setOnClickListener(this)
        binding.btnDemo3.setOnClickListener(this)
        binding.btnDemo4.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

        when(view!!.id) {
            binding.btnDemo1.id!! -> {

                startActivity(Intent(this@ViewModelMainActivity, DemoViewmodelActivity::class.java))
                // showDialog("Data is Loading")

            }

            binding.btnDemo2.id!! -> {

                startActivity(Intent(this@ViewModelMainActivity, ViewModeDemo2Activity::class.java))
                // showDialog("Data is Loading")

            }

            binding.btnDemo3.id!! -> {

                startActivity(Intent(this@ViewModelMainActivity, ViewModelDemo3Activity::class.java))
                // showDialog("Data is Loading")

            }

            binding.btnDemo4.id!! -> {

                startActivity(Intent(this@ViewModelMainActivity, LiveDataDemoActivity::class.java))
                // showDialog("Data is Loading")

            }
        }
    }
}