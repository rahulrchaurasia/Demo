package com.example.jetpackdemo.HiltDemo

import android.util.Log
import com.example.jetpackdemo.Utility.Constant
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton


interface IOne{

    fun getData()
}

class MyOne   @Inject constructor(private val data:Int): IOne {


    override fun getData() {

        Log.d(Constant.TAG,"IOne Implement by ONe, My name is ${data}")
    }

}

class Main @Inject  constructor(private  val iOne: IOne){

    fun getName(){

        iOne.getData()
    }
}


