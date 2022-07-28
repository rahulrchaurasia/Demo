package com.example.jetpackdemo.RoomDemo.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jetpackdemo.RoomDemo.Entity.Contact

@Dao
interface ConatactDao {


    @Insert
   suspend fun insertContact(contact: Contact)


    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)


//    @Query("select * from contact" )
//    suspend fun getContact() : LiveData<List<Contact>>    // Since we use Live data its bydefault execute in background, no need suspend fun


}