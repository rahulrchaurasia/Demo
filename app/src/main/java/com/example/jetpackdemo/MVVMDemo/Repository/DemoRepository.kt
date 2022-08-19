package com.example.jetpackdemo.MVVMDemo.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.jetpackdemo.MVVMDemo.API.DemoAPI
import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantDataResponse
import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantEntity
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field


//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050

class DemoRepository(
    private val demoAPI: DemoAPI,
    private val demoDatabase: DemoDatabase
){


//    private val userConstantMutableLiveData = MutableLiveData<ConstantDataResponse>()
//
//    val userConstant : LiveData<ConstantDataResponse>
//    get() =  userConstantMutableLiveData




    suspend fun getUserConstandDataFromAPI(@Body body: HashMap<String, String>) =   demoAPI.getConstant(body)


    // For Using URL Encoded Example

    suspend fun getUserConstandDataFromAPI1( @Field("fbaid") fbaID: String,
                                             @Field("appTypeId") appTypeId: String) =   demoAPI.getConstant1(fbaID,appTypeId)

    // suspend fun insertArticle(article: Article) = articleDao.insert(article)



    suspend fun getUserConstandDataUsingWithFromAPI(@Body body: HashMap<String, String>): Response<ConstantDataResponse> = withContext(Dispatchers.IO) {

        demoAPI.getConstant(body)
        val result =  demoAPI.getConstant(body)


        ////////

        if(result?.body() != null){
            Log.d(Constant.TAG_Coroutine,"Data Come from service")

            demoDatabase.constantDao().createConstant(result.body()!!.MasterData)

        }

        return@withContext result

    }

}