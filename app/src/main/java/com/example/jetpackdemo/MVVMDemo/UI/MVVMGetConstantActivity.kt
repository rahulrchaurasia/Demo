package com.example.jetpackdemo.MVVMDemo.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.MVVMDemo.API.DemoAPI
import com.example.jetpackdemo.MVVMDemo.Repository.DemoRepository
import com.example.jetpackdemo.MVVMDemo.ViewModel.DemoViewModel
import com.example.jetpackdemo.MVVMDemo.ViewModel.Factory.DemoViewModelFactory
import com.example.jetpackdemo.Response
import com.example.jetpackdemo.RetrofitHelper
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityMvvmgetConstantBinding
import com.google.android.material.snackbar.Snackbar

// For refrence
//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050


class MVVMGetConstantActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding:ActivityMvvmgetConstantBinding
    lateinit var viewModel: DemoViewModel
    lateinit var viewModelFactory :DemoViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMvvmgetConstantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("Nested Data Save In Room")

        }
        var retrofitApi = RetrofitHelper.getInstance().create(DemoAPI::class.java)
        val database = DemoDatabase.getDatabase(this)
        var repository = DemoRepository(retrofitApi,database)



        viewModelFactory = DemoViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(DemoViewModel::class.java)


      //  viewModel.getUserConstandDataFromAPI1(fbaID = "1976")      // api using Encoded URL


  //loanselfname

        // When API is called it will set the Live Data ie constantData
        // we applied observer on Live Data ie constantData , when data is set on it automatically our view observe it  and triggered.
        // the data which is get by live Data is "it" form (lembda expression)
        viewModel.constantData.observe(this ,{

            when(it){

                is Response.Loading -> {

                    showDialog()
                }

                is Response.Success -> {
                    cancelDialog()
                        it.data?.let {

                           binding.includeMvvmConstant.txtResult.text = it.MasterData.loanselfname.toString()
                           Log.d(Constant.TAG_Coroutine, it.toString())



                        }



                }

                is Response.Error -> {
                    cancelDialog()
                    Snackbar.make(binding.toolbar,it.errorMessage.toString(), Snackbar.LENGTH_SHORT).show()
                    Log.d(Constant.TAG_Coroutine, it.errorMessage.toString())
                }



            }

        })


        binding.includeMvvmConstant.btnDemo1.setOnClickListener(this)
        binding.includeMvvmConstant.btnDemo2.setOnClickListener(this)
        binding.includeMvvmConstant.btnDemo3.setOnClickListener(this)



    }

    override fun onClick(view: View?) {
      when(view!!.id){

          binding.includeMvvmConstant.btnDemo1.id -> {
              viewModel.getUserConstandDataFromAPI(fbaID = "1976")      // here we call api

          }
          binding.includeMvvmConstant.btnDemo2.id -> {
              viewModel.getUserConstandDataFromAPI1(fbaID = "1976")      // here we call api

          }

          binding.includeMvvmConstant.btnDemo3.id -> {
              viewModel.getUserConstandDataUsingWithFromAPI(fbaID = "1976")      // here we call api

          }


      }
    }
}