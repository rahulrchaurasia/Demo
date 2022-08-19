package com.example.jetpackdemo.MVVMDemo.Data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey



data class quoteResponse(

    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<QuoteEntity>,
    val totalCount: Int,
    val totalPages: Int
)