package com.example.jetpackdemo.HomePage

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.CoroutineDemo.CoroutineDemo1Activity

import com.example.jetpackdemo.KotlinDemo.KotlinBasicDemoActivity
import com.example.jetpackdemo.Notification.NotificationActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.RoomDemo.RoomDemoMainActivity
import com.example.jetpackdemo.Scanner.ScannerActivity
import com.example.jetpackdemo.ServiceDemo.ServiceDemoActivity
import com.example.jetpackdemo.ViewModelDemo.ViewModelMainActivity
import com.example.jetpackdemo.ViewModelShareDemo.DemoViewmodelActivity
import com.example.jetpackdemo.WorkManager.WorkManagerDemoActivity
import com.example.jetpackdemo.databinding.ActivityHomePageBinding


class HomePageActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding:ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
          //  requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        binding = ActivityHomePageBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

       // supportActionBar?.hide()
      // theme.applyStyle(R.style.FullScreen,false)

        val  include = binding.includeHomepage
        include.btnKotlinBasic.setOnClickListener(this)
        include.btnViewModelDemo.setOnClickListener(this)
        include.btnCoroutine.setOnClickListener(this)
        include.btnWorkManagerDemo.setOnClickListener(this)
        include.btnNotificationDemo.setOnClickListener(this)
        include.btnServiceDemo.setOnClickListener(this)
        include.btnScannerDemo.setOnClickListener(this)
        include.btnRoom.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

        when(view!!.id){

            binding.includeHomepage.btnKotlinBasic.id!! -> {

                startActivity(Intent(this@HomePageActivity, KotlinBasicDemoActivity::class.java))
               // showDialog("Data is Loading")

            }

            binding.includeHomepage.btnViewModelDemo.id!! -> {

               startActivity(Intent(this@HomePageActivity, ViewModelMainActivity::class.java))


            }

            binding.includeHomepage.btnCoroutine.id ->{

                startActivity(Intent(this@HomePageActivity, CoroutineDemo1Activity::class.java))

            }

            binding.includeHomepage.btnRoom.id!! -> {

                startActivity(Intent(this@HomePageActivity, RoomDemoMainActivity::class.java))


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

            binding.includeHomepage.btnScannerDemo.id!! -> {

                startActivity(Intent(this@HomePageActivity, ScannerActivity::class.java))

            }


        }


    }



}