package com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity

import com.example.jetpackdemo.LoginModule.DataModel.ApiResponse

data class LoginResponse (
    val MasterData: LoginEntity

): ApiResponse()