package com.example.jetpackdemo.LoginModule.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity.DashboardResponse
import com.example.jetpackdemo.LoginModule.Repository.DashboardRepository
import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantDataResponse
import com.example.jetpackdemo.MVVMDemo.Repository.DemoRepository
import com.example.jetpackdemo.Response
import com.example.jetpackdemo.Utility.Constant
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.http.Field
import kotlin.math.log

class DashboardViewModel(private  val repository: DashboardRepository) : ViewModel() {

   private val _constantData = MutableLiveData<Response<ConstantDataResponse>>()
    private val _dashBoardData = MutableLiveData<Response<DashboardResponse>>()

    val constantData : LiveData<Response<ConstantDataResponse>>
    get() = _constantData

    val dashBoardDataLiveData : LiveData<Response<DashboardResponse>>
    get() = _dashBoardData


    //No need to Observe UseConstand Api ie constantData
    fun getParallelAPIForUser_Dasbboard(fbaID : String){

        _dashBoardData.postValue(Response.Loading())    // Calling Loading Function

        val body = HashMap<String, String>()
        body["fbaid"] = fbaID

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val resultDashboardAsync = async {  repository.getDynamicDashBoard(body)}

                val resultConstantAsync = async {  repository.getUserConstandDataUsingWithFromAPI(body) }


                val resultDashboard = resultDashboardAsync.await()
                val resultConstant = resultConstantAsync.await()

                //val result = repository.getDynamicDashBoard(body)
                withContext(Dispatchers.Main){
                    if(resultDashboard.isSuccessful  && resultConstant.isSuccessful){

                        // region DashBoardSuccess
                        if(resultDashboard.body()?.StatusNo == 0){
                            _dashBoardData.postValue(Response.Success(resultDashboard.body()) )  //For Dashboard

                        }else{
                            _dashBoardData.postValue(Response.Error(resultDashboard.body()!!.Message) )
                        }
                        //endregion



                    }else{

                        Log.d(Constant.TAG_Coroutine, resultDashboard.message())
                        _dashBoardData.postValue(Response.Error(resultDashboard.errorBody().toString()) )




                    }
                }
            } catch (exception: Exception) {
                _dashBoardData.postValue(Response.Error( "Error Occurred!" + exception.message) )
            }
        }
    }


    //If we wann to Observe Both constantData and dashBoardDataLiveData
    // But mostly when parallel api is called we don't required to observe all api.

    fun getParallelAPIForUser_Dasbboard_ObservingBoth(fbaID : String){

        _dashBoardData.postValue(Response.Loading())    // Calling Loading Function

        val body = HashMap<String, String>()
        body["fbaid"] = fbaID

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val resultDashboardAsync = async {  repository.getDynamicDashBoard(body)}

                val resultConstantAsync = async {  repository.getUserConstandDataUsingWithFromAPI(body) }


                val resultDashboard = resultDashboardAsync.await()
                val resultConstant = resultConstantAsync.await()

                //val result = repository.getDynamicDashBoard(body)
                withContext(Dispatchers.Main){
                    if(resultDashboard.isSuccessful  && resultConstant.isSuccessful){

                        // region DashBoard and UserConstant Success
                        // region DashBoardSuccess
                        if(resultDashboard.body()?.StatusNo == 0){
                            _dashBoardData.postValue(Response.Success(resultDashboard.body()) )  //For Dashboard

                        }else{
                            _dashBoardData.postValue(Response.Error(resultDashboard.body()!!.Message) )
                        }
                        //endregion

                        // region ConstantSuccess
                        if(resultConstant.body()?.StatusNo == 0){
                            _constantData.postValue(Response.Success(resultConstant.body()) )   // For Live
                        }else{
                            _constantData.postValue(Response.Error(resultConstant.body()!!.Message) )
                        }

                        //endregion

                        //endregion


                    }
                    else{
                        // region Handling Dashboard and Constant Failure
                        if(!resultDashboard.isSuccessful){
                            Log.d(Constant.TAG_Coroutine, resultDashboard.message())
                            _dashBoardData.postValue(Response.Error(resultDashboard.errorBody().toString()) )

                        }
                        if(!resultConstant.isSuccessful) {
                            Log.d(Constant.TAG_Coroutine, resultConstant.message())
                            _constantData.postValue(Response.Error(resultConstant.errorBody().toString()))
                        }
                        //endregion


                    }
                }
            } catch (exception: Exception) {
                _dashBoardData.postValue(Response.Error( "Error Occurred!" + exception.message) )
            }
        }
    }



}