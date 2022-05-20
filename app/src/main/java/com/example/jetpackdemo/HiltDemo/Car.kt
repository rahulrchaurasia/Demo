package com.example.jetpackdemo.HiltDemo

import android.util.Log
import com.example.jetpackdemo.Utility.Constant
import javax.inject.Inject

class Car @Inject constructor(private val engine: Engine , private val  wheel: Wheel) {


    fun getCar(){

        engine.getEngine()
        wheel.getWheel()
        Log.d(Constant.TAG_HILT, "car is running")

    }
}