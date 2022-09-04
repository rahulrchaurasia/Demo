package com.example.jetpackdemo.MVVMDemo.API

import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantDataResponse
import com.example.jetpackdemo.RetrofitHelper
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface DemoAPI {


//    @Headers("token:"+ RetrofitHelper.token)
//    @GET("/quote/Postfm/user-constant-pb")
//    suspend fun getConstant(@Body body: HashMap<String, String>) : Call<quoteResponse>

    /***********  Note  **************
     Call<quoteResponse>   is Retrofit method  insteda we have to use

    Response<quoteResponse> Coroutine Method for getting the result.
    ************************/

    @FormUrlEncoded
    @Headers("token:"+ RetrofitHelper.token)
    @POST("/quote/Postfm/user-constant-pb")
    suspend fun getConstant1(
        @Field("fbaid") fbaID: String,
        @Field("appTypeId") appTypeId: String
    ) : Response<ConstantDataResponse>



    @Headers("token:"+ RetrofitHelper.token)
    @POST("/quote/Postfm/user-constant-pb")
    suspend fun getConstant(@Body body: HashMap<String, String>) : Response<ConstantDataResponse>


}