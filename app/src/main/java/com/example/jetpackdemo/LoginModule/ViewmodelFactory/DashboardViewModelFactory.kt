package com.example.jetpackdemo.LoginModule.ViewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.LoginModule.Repository.DashboardRepository
import com.example.jetpackdemo.LoginModule.ViewModel.DashboardViewModel
import com.example.jetpackdemo.LoginModule.ViewModel.LoginViewModel

class DashboardViewModelFactory(private val repository: DashboardRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(DashboardViewModel::class.java)){

            return DashboardViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel class Not Found")

    }
}