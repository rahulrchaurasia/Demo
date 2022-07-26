package com.example.jetpackdemo.RoomDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jetpackdemo.databinding.ActivityRoomDemoBinding

class RoomDemoActivity : AppCompatActivity() {

    lateinit var binding : ActivityRoomDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("Room Demo 1")
        }

    }
}