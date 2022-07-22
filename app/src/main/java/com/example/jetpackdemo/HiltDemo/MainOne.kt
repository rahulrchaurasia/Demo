package com.example.jetpackdemo.HiltDemo

import android.util.Log
import com.example.jetpackdemo.Utility.Constant
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


//Todo : @Binds : Annotation used when we creating dependency injection for interface
//Todo : @Provides  Annotation used when we creating dependency injection for library
//interface DemoOne{
//
//    fun demoOne()
//}
//
//class DemoImplementationOne @Inject constructor(): DemoOne {
//
//    override fun demoOne() {
//
//        Log.d(Constant.TAG_HILT,"Demo One Implemented using Binds")
//    }
//
//
//}
//
//class MainOne @Inject constructor (private val demoOne: DemoOne){
//
//    fun demoOne(){
//
//        demoOne.demoOne()
//    }
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class AppModule2{
//
//    @Binds
//    @Singleton
//    abstract fun  provideDemoOne(demoImplementationOne: DemoImplementationOne ) : DemoOne
//}