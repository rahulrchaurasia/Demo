package com.example.jetpackdemo.TrucallerModel.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CallerDialoViewModel : ViewModel() {

    private val callerMutableLiveData = MutableLiveData<Boolean>()

    val callerLiveData : LiveData<Boolean>
        get() = callerMutableLiveData

    init {

        callerMutableLiveData.value = false
    }



    fun isCallerEnd(boolean: Boolean){

        callerMutableLiveData.value = boolean
    }
}