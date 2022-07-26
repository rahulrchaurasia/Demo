package com.example.jetpackdemo.ViewModelDemo.ViewModelFactoryDemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelDemo3(var tCounter : Int) : ViewModel() {


    var count : Int = 0




    init {

        count = tCounter
    }


    fun getCountData() : Int{

       return count ++
    }




}