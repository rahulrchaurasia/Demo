package com.example.jetpackdemo.HiltDemo.MobileDemo

import android.util.Log
import androidx.room.Index
import com.example.jetpackdemo.Utility.Constant
import javax.inject.Inject

class Mobile @Inject constructor(var battery: Battery, var processor: Processor) {

    init {
        Log.d(Constant.TAG, "Mobile init...")
    }
    fun MobileData(){

        Log.d(Constant.TAG, "Mobile Data Started...")
    }
}