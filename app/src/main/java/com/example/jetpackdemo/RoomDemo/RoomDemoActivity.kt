package com.example.jetpackdemo.RoomDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.RoomDemo.Entity.Contact
import com.example.jetpackdemo.databinding.ActivityRoomDemoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomDemoActivity : AppCompatActivity() {

    lateinit var binding : ActivityRoomDemoBinding
    lateinit var  database: DemoDatabase
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

        database = DemoDatabase.getDatabase(this@RoomDemoActivity)


        binding.includeRoomDemo1.btnSubmit.setOnClickListener {

                if(validate()){

                   GlobalScope.launch {

                       val contact = Contact(id = 0, name = binding.includeRoomDemo1.etName.text.toString(),
                                             mobile = binding.includeRoomDemo1.etMobile.text.toString(),
                                            address = binding.includeRoomDemo1.etAddress.text.toString() )
                       database.contactDao().insertContact(contact)

                       Snackbar.make(binding.includeRoomDemo1.btnSubmit, "Data Save Successfully !!", Snackbar.LENGTH_SHORT).show()
                   }

                }

        }

    }

    fun validate() : Boolean {

       var viewBtn = binding.includeRoomDemo1.btnSubmit
       var blnCheck : Boolean = true

       if(binding.includeRoomDemo1.etName.text.isNullOrEmpty()){

           Snackbar.make(viewBtn, "Required Name!!", Snackbar.LENGTH_SHORT).show()
           blnCheck = false
       }else if(binding.includeRoomDemo1.etMobile.text.isNullOrEmpty()){

            Snackbar.make(viewBtn, "Required Mobile No!!", Snackbar.LENGTH_SHORT).show()
            blnCheck = false
        }else if(binding.includeRoomDemo1.etMobile.text.length < 10){

           Snackbar.make(viewBtn, "Mobile No must be 10 digit!!", Snackbar.LENGTH_SHORT).show()
           blnCheck = false
       }else if(binding.includeRoomDemo1.etAddress.text.isNullOrEmpty()){

           Snackbar.make(viewBtn, "Required Address !!", Snackbar.LENGTH_SHORT).show()
           blnCheck = false
       }


       return blnCheck
    }


}