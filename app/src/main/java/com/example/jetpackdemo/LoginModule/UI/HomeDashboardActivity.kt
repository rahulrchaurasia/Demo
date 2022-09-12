package com.example.jetpackdemo.LoginModule.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.LoginModule.Repository.DashboardRepository
import com.example.jetpackdemo.LoginModule.Repository.LoginRepository
import com.example.jetpackdemo.LoginModule.ViewModel.DashboardViewModel
import com.example.jetpackdemo.LoginModule.ViewModel.LoginViewModel
import com.example.jetpackdemo.LoginModule.ViewmodelFactory.DashboardViewModelFactory
import com.example.jetpackdemo.LoginModule.ViewmodelFactory.LoginViewModelFactory
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Response
import com.example.jetpackdemo.RetrofitHelper
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityHomeDashboardBinding
import com.google.android.material.snackbar.Snackbar

/****************************************************************************************************
 * Task For Async Task parallel :-- > we Call  UserConstant ans DynamicDashboard Api parallel
// https://www.youtube.com/watch?v=dTqOVsdj0pY&t=555s
//https://discuss.kotlinlang.org/t/how-to-measure-execution-time-of-an-aync-query-request-inside-kotlin-coroutines/23352

 ****************************************************************************************************/
class HomeDashboardActivity : BaseActivity() {

    lateinit var bindingRoot : ActivityHomeDashboardBinding
    lateinit var layout: View

    lateinit var viewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRoot = ActivityHomeDashboardBinding.inflate(layoutInflater)
        setContentView(bindingRoot.root)

        layout = bindingRoot.root

        init()

        viewModel.getParallelAPIForUser_Dasbboard(fbaID = "89158")

        //viewModel.getParallelAPIForUser_Dasbboard_ObservingBoth(fbaID = "89158")
        getDashboardResponse()
        getUserConstantResponse()



    }
    private fun init(){

        var demoDatabase = DemoDatabase.getDatabase(applicationContext)
        var dashboardRepository = DashboardRepository(RetrofitHelper.retrofitLoginApi,demoDatabase)
        var viewModelFactory = DashboardViewModelFactory(dashboardRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(DashboardViewModel::class.java)


    }

    private fun getDashboardResponse(){

        viewModel.dashBoardDataLiveData.observe(this, {

            when(it){

                is Response.Loading ->{
                    showDialog()
                }

                is Response.Success -> {

                    cancelDialog()

                    it.data?.let {
                        Log.d(Constant.TAG_Coroutine +" Dasbboard :", it.toString())


                    }
                }

                is Response.Error -> {
                    cancelDialog()
                    Snackbar.make(layout,it.errorMessage.toString(), Snackbar.LENGTH_SHORT).show()
                    Log.d(Constant.TAG_Coroutine, it.errorMessage.toString())
                }
            }
        })

    }

    private fun getUserConstantResponse(){

        viewModel.constantData.observe(this, {

            when(it){

                is Response.Loading ->{
                    //showDialog()
                }

                is Response.Success -> {

                   // cancelDialog()

                    it.data?.let {
                        Log.d(Constant.TAG_Coroutine + " UserConstant :", it.toString())


                    }
                }

                is Response.Error -> {
                  //  cancelDialog()
                    Snackbar.make(layout,it.errorMessage.toString(), Snackbar.LENGTH_SHORT).show()
                    Log.d(Constant.TAG_Coroutine, it.errorMessage.toString())
                }
            }
        })

    }
    //getParallelAPIForUser_Dasbboard
}