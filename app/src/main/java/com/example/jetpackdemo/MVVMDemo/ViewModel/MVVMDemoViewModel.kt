package com.example.jetpackdemo.MVVMDemo.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.MVVMDemo.Data.QuoteEntity
import com.example.jetpackdemo.MVVMDemo.Data.quoteResponse
import com.example.jetpackdemo.MVVMDemo.Repository.QuoteRepository
import com.example.jetpackdemo.ViewModelDemo.ViewModelMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MVVMDemoViewModel(private val repository: QuoteRepository) : ViewModel() {

    val quote : LiveData<List<QuoteEntity>>
    get() = repository.quotes
    
    init {
        viewModelScope.launch(Dispatchers.IO){

            repository.getQuote(1  )
        }

    }
}