package com.example.jetpackdemo.HiltDemo

import android.util.Log
import com.example.jetpackdemo.Utility.Constant
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

//
//class QualifierDemo @Inject constructor (
//    @Fname
//    private val fName : String,
//    @Lname
//    private val lName : String){
//
//    fun getName(){
//
//        Log.d(Constant.TAG_HILT,"my name is $fName and $lName ")
//    }
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//class QualifierModuleApp{
//
//    @Provides
//    @Fname
//    fun getName() : String = "Rahul"
//
//    @Provides
//    @Lname
//    fun getLastName() : String = "Chaurasia"
//
///************************************************************/
//// For Main KT ModuleApp
//
//    @Provides
//    @Singleton
//    fun getNumber() : Int = 100
//
//   @Provides
//   @Singleton
//    fun binding(name: Int):IOne = MyOne(name )
//}
//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class Fname
//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class Lname
//
//
