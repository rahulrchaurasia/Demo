package com.example.jetpackdemo.ViewModelDemo.ViewModelFactoryDemo


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactoryDemo3(private var counter : Int) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ViewModelDemo3::class.java)){

            return ViewModelDemo3(counter) as T
         }

        throw IllegalArgumentException("ViewModel Class Not Found")
    }



}