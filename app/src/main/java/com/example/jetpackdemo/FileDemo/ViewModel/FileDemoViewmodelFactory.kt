package com.example.jetpackdemo.FileDemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.MVVMDemo.Repository.DemoRepository
import com.example.jetpackdemo.MVVMDemo.ViewModel.DemoViewModel
import java.io.File

class FileDemoViewmodelFactory (private val fileDir: File) : ViewModelProvider.Factory  {



    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // return super.create(modelClass)

        if(modelClass.isAssignableFrom(FileDemoViewMoldel::class.java)){

            return FileDemoViewMoldel(fileDir) as T
        }
        throw IllegalArgumentException("ViewModel Class Not Found")
    }


}