package com.example.jetpackdemo.LoginModule.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.LoginModule.DataModel.ResponseEntity.LoginResponse
import com.example.jetpackdemo.LoginModule.Repository.LoginRepository
import com.example.jetpackdemo.APIState
import com.example.jetpackdemo.LoginModule.DataModel.RequestEntity.LoginRequestEntity
import com.example.jetpackdemo.Response
import com.example.jetpackdemo.Utility.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel (var loginRepository: LoginRepository) : ViewModel(){


    private val loginMutableStateFlow  :  MutableStateFlow<APIState<LoginResponse>> =

        MutableStateFlow<APIState<LoginResponse>>(APIState.Empty())

    val LoginStateFlow : StateFlow<APIState<LoginResponse>>
    get() = loginMutableStateFlow



    private val loginMutableLiveData =  MutableLiveData<APIState<LoginResponse>>()

    val loginLiveData : LiveData<APIState<LoginResponse>>
    get() = loginMutableLiveData


    fun getLoginUsingFlow(loginRequestEntity: LoginRequestEntity) = viewModelScope.launch {

        loginMutableStateFlow.value = APIState.Loading()

        try {

            loginRepository.getLogin(loginRequestEntity)

                .catch { ex ->

                    // emit(APIState.Failure(ex.message ?: "Unknown Error"))
                    loginMutableStateFlow.value = APIState.Failure(ex.message ?: "Unknown Error")


                }.collect{ data ->

                    if(data.isSuccessful){
                        if(data.body()?.StatusNo == 0){


                            loginMutableStateFlow.value = APIState.Success(data =  data.body()!!)
                        }else{

                            loginMutableStateFlow.value = APIState.Failure(data.body()?.Message ?: Constant.ErrorMessage)

                        }
                    }else{
                        loginMutableStateFlow.value = APIState.Failure(data.message() ?: Constant.ErrorMessage)
                    }




                }

        }catch (ex : Exception){

            loginMutableStateFlow.value = APIState.Failure(ex.message ?: "Unknown Error")

        }

    }



    fun getLoginUsingLiveData(loginRequestEntity: LoginRequestEntity) = viewModelScope.launch {




        loginRepository.getLogin2(loginRequestEntity).collect{

            loginMutableLiveData.postValue(it)
        }


    }

}