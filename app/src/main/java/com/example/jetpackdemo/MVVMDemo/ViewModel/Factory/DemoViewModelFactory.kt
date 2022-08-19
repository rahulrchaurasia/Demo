package com.example.jetpackdemo.MVVMDemo.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.MVVMDemo.Repository.DemoRepository
import com.example.jetpackdemo.MVVMDemo.ViewModel.DemoViewModel

class DemoViewModelFactory(private val repository: DemoRepository) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       // return super.create(modelClass)

        if(modelClass.isAssignableFrom(DemoViewModel::class.java)){

            return DemoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel Class Not Found")
    }
}