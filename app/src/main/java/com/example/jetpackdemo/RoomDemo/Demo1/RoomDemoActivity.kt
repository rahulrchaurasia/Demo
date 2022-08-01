package com.example.jetpackdemo.RoomDemo.Demo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.RoomDemo.Demo1.ViewModelRoom.RoomDemo1ViewModel
import com.example.jetpackdemo.RoomDemo.Demo1.ViewModelRoom.RoomDemo1ViewModelFactory
import com.example.jetpackdemo.RoomDemo.Entity.Contact
import com.example.jetpackdemo.databinding.ActivityRoomDemoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomDemoActivity : AppCompatActivity() {


    lateinit var binding : ActivityRoomDemoBinding
    lateinit var  database: DemoDatabase
    lateinit var viewModel: RoomDemo1ViewModel
    lateinit var viewModelFactory: RoomDemo1ViewModelFactory


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
        viewModelFactory = RoomDemo1ViewModelFactory(database)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RoomDemo1ViewModel::class.java)

        binding.includeRoomDemo1.btnSubmit.setOnClickListener {

                if(validate()){

                   GlobalScope.launch {

                       val contact = Contact(id = 0, name = binding.includeRoomDemo1.etName.text.toString(),
                                             mobile = binding.includeRoomDemo1.etMobile.text.toString(),
                                            address = binding.includeRoomDemo1.etAddress.text.toString() )
                       database.contactDao().insertContact(contact)

                       Snackbar.make(binding.includeRoomDemo1.btnSubmit, "Data Save Successfully !!", Snackbar.LENGTH_SHORT).show()
                       clearData()
                   }

                }

        }




        binding.fabView.setOnClickListener{
//
//            val data   = viewModel.contactList
//            println("Data of contact${data}")
//            var contactList  : LiveData<List<Contact>>
//            contactList = viewModel.contactList
//            Log.d("COROUTINE", contactList.value!!.get(0).name)
           // Snackbar.make(binding.includeRoomDemo1.btnSubmit, "Required Name!!", Snackbar.LENGTH_SHORT).show()

            lifecycleScope.launch {

                val data  = viewModel.contactList

                var contactList  : LiveData<List<Contact>>
                contactList = viewModel.contactList
                Log.d("COROUTINE", contactList.value!!.get(0).name)
            }
        }

    }

    fun clearData(){

        binding.includeRoomDemo1.apply {
            etName.setText("")
            etAddress.setText("")
            etMobile.setText("")
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