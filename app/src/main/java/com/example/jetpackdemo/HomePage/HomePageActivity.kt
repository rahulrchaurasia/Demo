package com.example.jetpackdemo.HomePage

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.jetpackdemo.BaseActivity

import com.example.jetpackdemo.KotlinDemo.KotlinBasicDemoActivity
import com.example.jetpackdemo.Notification.NotificationActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ServiceDemo.ServiceDemoActivity
import com.example.jetpackdemo.ViewModelShareDemo.DemoViewmodelActivity
import com.example.jetpackdemo.WorkManager.WorkManagerDemoActivity
import com.example.jetpackdemo.databinding.ActivityHomePageBinding


class HomePageActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding:ActivityHomePageBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomePageBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setTitle("Home")
        }

        val  include = binding.includeHomepage
        include.btnKotlinBasic.setOnClickListener(this)
        include.btnViewModelDemo.setOnClickListener(this)
        include.btnWorkManagerDemo.setOnClickListener(this)
        include.btnNotificationDemo.setOnClickListener(this)
        include.btnServiceDemo.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

        when(view!!.id){

            binding.includeHomepage.btnKotlinBasic.id!! -> {

                startActivity(Intent(this@HomePageActivity, KotlinBasicDemoActivity::class.java))
               // showDialog("Data is Loading")

            }

            binding.includeHomepage.btnViewModelDemo.id!! -> {

               startActivity(Intent(this@HomePageActivity, DemoViewmodelActivity::class.java))


            }

            binding.includeHomepage.btnWorkManagerDemo.id!! -> {

                startActivity(Intent(this@HomePageActivity, WorkManagerDemoActivity::class.java))


            }
            //NotificationActivity

            binding.includeHomepage.btnNotificationDemo.id!! -> {

                startActivity(Intent(this@HomePageActivity, NotificationActivity::class.java))


            }
            binding.includeHomepage.btnServiceDemo.id!! -> {

                startActivity(Intent(this@HomePageActivity, ServiceDemoActivity::class.java))


            }


        }


    }



}