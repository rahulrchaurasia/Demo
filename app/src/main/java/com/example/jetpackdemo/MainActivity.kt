package com.example.jetpackdemo


import android.os.Bundle
import com.example.jetpackdemo.HiltDemo.*
import com.example.jetpackdemo.HiltDemo.MobileDemo.ImpProcessor
import com.example.jetpackdemo.HiltDemo.MobileDemo.Mobile
//import com.example.jetpackdemo.HiltDemo.QualifierDemo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject



@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var car : Car

    @Inject
    lateinit var main: Main

    @Inject
    lateinit var qualifierDemo: QualifierDemo

    @Inject
    lateinit var mainOne: MainOne

    @Inject
    lateinit var mobile: Mobile

    @Inject
    lateinit var impProcessor: ImpProcessor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         car.getCar()

        main.getName()

       qualifierDemo.getName()

        mainOne.demoOne()       // dependency injection in Interface

        mobile.MobileData()

        mobile.processor.getProcess()

        impProcessor.getProcess()
    }
}