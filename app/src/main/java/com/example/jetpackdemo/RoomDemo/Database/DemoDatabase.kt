package com.example.jetpackdemo.RoomDemo.Database

import android.content.Context
import androidx.room.*
import com.example.jetpackdemo.MVVMDemo.Dao.ConstantDao
import com.example.jetpackdemo.MVVMDemo.Data.QuoteEntity
import com.example.jetpackdemo.RoomDemo.Convertors.Convertors
import com.example.jetpackdemo.RoomDemo.Dao.ConatactDao
import com.example.jetpackdemo.MVVMDemo.Dao.QuoteDao
import com.example.jetpackdemo.MVVMDemo.Data.DashboardData.ConstantEntity
import com.example.jetpackdemo.RoomDemo.Entity.Contact

/***************** Defination****************************
//volatile : --> property as volatile, meaning that writes to this field are immediately made visible to other threads.


 we have used Singleton pattern to Create RoomDatabase INSTANCE
 **********************************************/


@Database(entities = [Contact::class,QuoteEntity::class, ConstantEntity::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class DemoDatabase : RoomDatabase() {


    abstract fun contactDao() : ConatactDao

    abstract fun  quoteDao() : QuoteDao

    abstract fun  constantDao() : ConstantDao

    companion object{

        @Volatile
        private var INSTANCE: DemoDatabase? = null       // Volatile : here any changes in "Instance" are immediately made visible to all threads

        fun  getDatabase(context : Context) : DemoDatabase {

            if(INSTANCE == null){

                synchronized(this){                                         // Note synchronized : due to multithread it may
                                                                                    // chance  create multiple instance of Room Database.
                    INSTANCE = Room.databaseBuilder(context.applicationContext,    // Synchronize lock the particular instance.
                        DemoDatabase::class.java,
                        "DemoDB").build()
                }

            }

            return INSTANCE!!

        }
    }

}