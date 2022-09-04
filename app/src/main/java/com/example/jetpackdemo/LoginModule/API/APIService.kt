package com.example.jetpackdemo.LoginModule.API

import com.example.jetpackdemo.LoginModule.DataModel.RequestEntity.LoginRequestEntity
import com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity.LoginResponse
import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantDataResponse
import com.example.jetpackdemo.RetrofitHelper
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {


//    @Headers("token:"+ RetrofitHelper.token)
//    @GET("/quote/Postfm/user-constant-pb")
//    suspend fun getConstant(@Body body: HashMap<String, String>) : Call<quoteResponse>

    /***********  Note  **************
     Call<quoteResponse>   is Retrofit method  insteda we have to use

    Response<quoteResponse> Coroutine Method for getting the result.
    ************************/




    @Headers("token:"+ RetrofitHelper.token)
    @POST("/quote/Postfm/login")
    suspend fun getLogin(
        @Body body: LoginRequestEntity
    ) : Response<LoginResponse>


}