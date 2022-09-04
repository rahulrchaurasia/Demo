package com.example.jetpackdemo.MVVMDemo.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantDataResponse
import com.example.jetpackdemo.MVVMDemo.Repository.DemoRepository
import com.example.jetpackdemo.Response
import com.example.jetpackdemo.Utility.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import retrofit2.http.Field
import kotlin.math.log

class DemoViewModel(private  val repository: DemoRepository) : ViewModel() {

   private val _constantData = MutableLiveData<Response<ConstantDataResponse>>()

    val constantData : LiveData<Response<ConstantDataResponse>>
    get() = _constantData




    fun getUserConstandDataFromAPI(fbaID : String){

        _constantData.postValue(Response.Loading())    // Calling Loading Function

        val body = HashMap<String, String>()
        body["fbaid"] = fbaID
        body["appTypeId"] = "4"
        viewModelScope.launch(Dispatchers.IO) {

            delay(3000)
            try {
                val result = repository.getUserConstandDataFromAPI(body)
                withContext(Dispatchers.Main){
                    if(result.isSuccessful){
                        if(result.body()?.StatusNo == 0){

                            _constantData.postValue(Response.Success(result.body()) )


                        }else{
                            _constantData.postValue(Response.Error(result.body()!!.Message) )
                        }

                    }else{
                        Log.d(Constant.TAG_Coroutine, result.message())
                        _constantData.postValue(Response.Error(result.errorBody().toString()) )

                    }
                }
            } catch (exception: Exception) {
                _constantData.postValue(Response.Error( "Error Occurred!" + exception.message) )
            }

        }
    }


    // OR Using URL EnCoded

    fun getUserConstandDataFromAPI1(fbaID : String){

        _constantData.postValue(Response.Loading())    // Calling Loading Function

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = repository.getUserConstandDataFromAPI1(fbaID, "4")
                withContext(Dispatchers.Main){
                    if(result.isSuccessful){
                        if(result.body()?.StatusNo == 0){
                            _constantData.postValue(Response.Success(result.body()) )

                        }else{
                            _constantData.postValue(Response.Error(result.body()!!.Message) )
                        }

                    }else{
                        Log.d(Constant.TAG_Coroutine, result.message())
                        _constantData.postValue(Response.Error(result.errorBody().toString()) )

                    }
                }
            } catch (exception: Exception) {
                _constantData.postValue(Response.Error( "Error Occurred!" + exception.message) )
            }

        }
    }


    fun getUserConstandDataUsingWithFromAPI(fbaID : String){

        _constantData.postValue(Response.Loading())    // Calling Loading Function

        val body = HashMap<String, String>()
        body["fbaid"] = fbaID
        body["appTypeId"] = "4"
       viewModelScope.launch(Dispatchers.IO) {

           try {
               val result = repository.getUserConstandDataUsingWithFromAPI(body)
               withContext(Dispatchers.Main){
                   if(result.isSuccessful){
                       if(result.body()?.StatusNo == 0){
                           _constantData.postValue(Response.Success(result.body()) )

                       }else{
                           _constantData.postValue(Response.Error(result.body()!!.Message) )
                       }

                   }else{
                       Log.d(Constant.TAG_Coroutine, result.message())
                       _constantData.postValue(Response.Error(result.errorBody().toString()) )

                   }
               }
           } catch (exception: Exception) {
               _constantData.postValue(Response.Error( "Error Occurred!" + exception.message) )
           }
        }
    }



}