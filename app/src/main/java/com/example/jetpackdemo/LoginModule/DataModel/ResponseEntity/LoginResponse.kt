package com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity

import com.example.jetpackdemo.LoginModule.DataModel.ApiResponse
import com.example.jetpackdemo.LoginModule.DataModel.model.LoginEntity

data class LoginResponse (
    val MasterData: LoginEntity

): ApiResponse()