package com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity

import com.example.jetpackdemo.LoginModule.DataModel.ApiResponse
import com.example.jetpackdemo.LoginModule.DataModel.model.DashboardEntity


data class DashboardResponse(
    val MasterData: DashboardMasterData,

    ): ApiResponse()


data class DashboardMasterData(
    val Dashboard: MutableList<DashboardEntity>,
    val Menu: MutableList<Any>
)