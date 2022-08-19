package com.example.jetpackdemo.MVVMDemo.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.MVVMDemo.API.QuoteAPI
import com.example.jetpackdemo.MVVMDemo.Repository.QuoteRepository
import com.example.jetpackdemo.MVVMDemo.ViewModel.MVVMDemoViewModel
import com.example.jetpackdemo.MVVMDemo.ViewModel.Factory.MVVMDemoViewModelFactory
import com.example.jetpackdemo.RetrofitHelper
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityMvvmdemoBinding

// For refrence :
//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050

// 2 > Cheezy Code : MVVM

//https://www.youtube.com/watch?v=uIctBzZGPM8

class MVVMDemoActivity : AppCompatActivity() {

    lateinit var  binding: ActivityMvvmdemoBinding
    lateinit var viewModel: MVVMDemoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMvvmdemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("MVVM Demo")

        }

      //  val retrofitApi = RetrofitHelper.getInstance().create(QuoteAPI::class.java)


        val database = DemoDatabase.getDatabase(this)
        var repository = QuoteRepository(RetrofitHelper.retrofitQuoteApi,database,this@MVVMDemoActivity)

        var viewModelFactory = MVVMDemoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MVVMDemoViewModel::class.java)


        // we can observe live data
        // since quote is live Data define in viewmodel , we can apply observable on it.
        viewModel.quote.observe(this, {

            try {
                Log.d(Constant.TAG_Coroutine, it.toString())
            }catch (ex : Exception){

            }

        })



    }
}