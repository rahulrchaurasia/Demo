package com.example.jetpackdemo.RoomDemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpackdemo.RoomDemo.UI.DisplayRoomData.DisplayRoomDataActivity
import com.example.jetpackdemo.RoomDemo.UI.RoomDemoActivity
import com.example.jetpackdemo.databinding.ActivityRoomDemoMainBinding


class RoomDemoMainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityRoomDemoMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomDemoMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("Room Demo List")
        }

        binding.includeRoomMain.btnDemo1.setOnClickListener(this)
        binding.includeRoomMain.btnDemo2.setOnClickListener(this)


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onClick(view: View?) {

        when(view!!.id){

            binding.includeRoomMain.btnDemo1.id ->
            {
                startActivity(Intent(this@RoomDemoMainActivity, RoomDemoActivity::class.java))

            }

            binding.includeRoomMain.btnDemo2.id ->
            {
                startActivity(Intent(this@RoomDemoMainActivity, DisplayRoomDataActivity::class.java))

            }
        }
    }


}