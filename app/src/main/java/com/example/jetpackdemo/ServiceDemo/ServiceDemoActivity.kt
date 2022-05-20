package com.example.jetpackdemo.ServiceDemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.ServiceDemo.Service.ForegroundService
import com.example.jetpackdemo.databinding.ActivityServiceDemoBinding


class ServiceDemoActivity : BaseActivity() , View.OnClickListener {

    private lateinit var binding : ActivityServiceDemoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServiceDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayUseLogoEnabled(true)

        }

        val include= binding.includeService
        include.btnStart.setOnClickListener(this)
        include.btnStop.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

       when(view?.id){

           binding.includeService.btnStart.id -> {

               ForegroundService.startService(this@ServiceDemoActivity ,"This is Foreground Sevice" )
           }

           binding.includeService.btnStop.id -> {
               ForegroundService.stopService(this@ServiceDemoActivity)
           }
       }
    }


}