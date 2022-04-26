package com.example.jetpackdemo.HiltDemo

import android.util.Log
import com.example.jetpackdemo.Utility.Constant
import javax.inject.Inject

class Engine {
    @Inject
    constructor()
    fun getEngine(){

        Log.d(Constant.TAG,"Engine is running..")
    }
}