package com.example.jetpackdemo.HiltDemo

import android.util.Log
import com.example.jetpackdemo.Utility.Constant
import javax.inject.Inject


interface IOne{

    fun getData()
}

class MyOne   @Inject constructor(private val data:Int): IOne {


    override fun getData() {

        Log.d(Constant.TAG_HILT,"IOne Implement by ONe, My name is ${data}")
    }

}

class Main @Inject  constructor(private  val iOne: IOne){

    fun getName(){

        iOne.getData()
    }
}


