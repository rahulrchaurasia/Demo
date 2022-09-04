package com.example.jetpackdemo.MVVMDemo.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity.LoginEntity
import com.example.jetpackdemo.MVVMDemo.Data.QuoteEntity
import com.example.jetpackdemo.MVVMDemo.Data.quoteResponse

@Dao
interface LoginDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLoginData(loginEntity : LoginEntity)


//    @Query( "select * from Quote")
//    suspend fun getQuote(): List<QuoteEntity>
}