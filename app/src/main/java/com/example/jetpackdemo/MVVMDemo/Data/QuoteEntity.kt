package com.example.jetpackdemo.MVVMDemo.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quote")
data class QuoteEntity(

    @PrimaryKey(autoGenerate = true)
    val quoteId : Int,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,

)