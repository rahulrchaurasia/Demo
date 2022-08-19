package com.example.jetpackdemo.MVVMDemo.API

import com.example.jetpackdemo.MVVMDemo.Data.quoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface QuoteAPI {

   // https://quotable.io/quotes?page=1
   @GET("/quotes")
    suspend fun getQuotes( @Query("page") page : Int) : Response<quoteResponse>
}