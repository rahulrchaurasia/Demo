package com.example.jetpackdemo.RoomDemo.Demo1.ViewModelRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.RoomDemo.Entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomDemo1ViewModel(val database: DemoDatabase) : ViewModel() {



    private val _contactList = MutableLiveData<List<Contact>>()

    // Required LiveData is only public therefore data ,  get() = _data means set mutabledata to it
    val contactList :LiveData<List<Contact>>
        get() = _contactList


    init {

        viewModelScope.launch {

            _contactList.value =  getContactData()
        }

    }


    private suspend fun getContactData() :List<Contact>{

        return withContext(Dispatchers.IO){
            database.contactDao().getContact()
        }
    }
}