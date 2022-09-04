package com.example.jetpackdemo.RoomDemo.UI.ViewModelRoom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase

class RoomDemo1ViewModelFactory(private val database: DemoDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       // return super.create(modelClass)
        if(modelClass.isAssignableFrom(RoomDemo1ViewModel::class.java)){

            return RoomDemo1ViewModel(database) as T
        }
        throw IllegalArgumentException("ViewModel Class Not Found")
    }
}