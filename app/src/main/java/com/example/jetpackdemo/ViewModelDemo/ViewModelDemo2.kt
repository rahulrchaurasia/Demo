package com.example.jetpackdemo.ViewModelDemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelDemo2 : ViewModel() {

    val data = MutableLiveData<String>()

    fun setData(newData : String){

        data.value = newData
    }
}