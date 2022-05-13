package com.example.jetpackdemo.ServiceDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ServiceDemo.Service.ForegroundService
import com.example.jetpackdemo.databinding.ActivityServiceDemoBinding

class ServiceDemoActivity : BaseActivity() , View.OnClickListener {

    lateinit var toolbar: Toolbar
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
            //setTitle("Work Manger Demo")
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