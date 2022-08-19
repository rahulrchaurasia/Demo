package com.example.jetpackdemo.MVVMDemo.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackdemo.MVVMDemo.Data.QuoteEntity
import com.example.jetpackdemo.MVVMDemo.Data.quoteResponse

@Dao
interface QuoteDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuote(quote : List<QuoteEntity>)


    @Query( "select * from Quote")
    suspend fun getQuote(): List<QuoteEntity>
}