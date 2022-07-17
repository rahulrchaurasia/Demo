package com.example.jetpackdemo.KotlinDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityKotlinBasicDemoBinding

class KotlinBasicDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKotlinBasicDemoBinding

    val fname : String = "umesh"
    val name : String? = null
    val lname : String? = null

    var arryName : ArrayList<String>? = null

    var list : MutableList<String> = ArrayList()    // mostly used



    val mutableList = mutableListOf<String>()

    val arrayList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityKotlinBasicDemoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("KOTLIN DEMO")
        }

        // non-null asserted !!
        Log.d(Constant.TAG_KOTLIN, fname!!.length.toString())


        // safe call

        Log.d(Constant.TAG_KOTLIN, name?.length.toString())

        // safe call with let

        name?.let {
            Log.d(Constant.TAG_KOTLIN, "length is ${name.length} ")
        }

        // Elvis Operator

        val len = lname?.length ?: "1"
        Log.d(Constant.TAG_KOTLIN, len.toString())


      // var myData = arryName?.size

        list.size



    }
}