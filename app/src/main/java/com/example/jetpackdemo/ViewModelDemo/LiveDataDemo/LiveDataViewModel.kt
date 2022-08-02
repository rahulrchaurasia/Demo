package com.example.jetpackdemo.ViewModelDemo.LiveDataDemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {

    val _factLiveData = MutableLiveData<String>("This is fact")

    val factLiveData: LiveData<String>
    get() = _factLiveData

    fun updateLiveData(){

        _factLiveData.value = "Another fact"
    }
}