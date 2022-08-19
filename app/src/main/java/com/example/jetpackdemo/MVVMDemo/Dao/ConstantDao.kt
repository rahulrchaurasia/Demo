package com.example.jetpackdemo.MVVMDemo.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantEntity
import com.example.jetpackdemo.MVVMDemo.Data.QuoteEntity
import com.example.jetpackdemo.MVVMDemo.Data.quoteResponse

@Dao
interface ConstantDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun createConstant(quote : ConstantEntity)


//    @Query( "select * from Quote")
//    suspend fun getQuote(): List<QuoteEntity>
}