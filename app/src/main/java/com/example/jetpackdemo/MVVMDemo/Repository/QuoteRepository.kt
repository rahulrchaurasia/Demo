package com.example.jetpackdemo.MVVMDemo.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.MVVMDemo.Data.QuoteEntity
import com.example.jetpackdemo.MVVMDemo.API.QuoteAPI
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.Utility.NetworkUtils

class QuoteRepository(private val quoteAPI: QuoteAPI,
                      private val demoDatabase: DemoDatabase,
                     private val applicationContext: Context) {


    // Note : from api we are returing list ie why use List<QuoteEntity
    private val quoteMutableData = MutableLiveData<List<QuoteEntity>>()

    val quotes : LiveData<List<QuoteEntity>>
    get() = quoteMutableData


    suspend fun getQuote(page : Int){

        if(NetworkUtils.isNetworkAvailable(applicationContext)){

            val result = quoteAPI.getQuotes(page)

            if(result?.body() != null){
                Log.d(Constant.TAG_Coroutine,"Data Come from service")

                demoDatabase.quoteDao().addQuote(result.body()!!.results)
                quoteMutableData.postValue(result.body()!!.results)

            }
        }else{

            Log.d(Constant.TAG_Coroutine,"Data Come from Database")
            val  quoteList  = demoDatabase.quoteDao().getQuote()

            quoteMutableData.postValue(quoteList)



        }



    }

}