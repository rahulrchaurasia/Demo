package com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity

import com.example.jetpackdemo.LoginModule.DataModel.APIResponse
import com.example.jetpackdemo.LoginModule.DataModel.model.DashboardEntity


data class DashboardResponse(
    val MasterData: DashboardMasterData,

    ): APIResponse()


data class DashboardMasterData(
    val Dashboard: MutableList<DashboardEntity>,
    val Menu: MutableList<Any>
)