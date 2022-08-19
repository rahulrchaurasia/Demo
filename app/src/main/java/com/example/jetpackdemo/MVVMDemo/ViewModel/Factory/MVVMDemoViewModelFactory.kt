package com.example.jetpackdemo.MVVMDemo.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.MVVMDemo.Repository.QuoteRepository
import com.example.jetpackdemo.MVVMDemo.ViewModel.MVVMDemoViewModel

class MVVMDemoViewModelFactory(private val repository: QuoteRepository) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MVVMDemoViewModel::class.java)){

            return MVVMDemoViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel Class Not Found")
    }
}