package com.example.jetpackdemo.HiltDemo.MobileDemo

import android.util.Log
import com.example.jetpackdemo.Utility.Constant
import javax.inject.Inject

class Battery @Inject constructor(){

    fun BatteryData(){

        Log.d(Constant.TAG, "Battery Data Started...")
    }

}