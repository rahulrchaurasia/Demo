package com.example.jetpackdemo

sealed class APIState<T> ( val data: T? = null,
                           val errorMessage: String? = null){
     class Loading<T> : APIState<T>()
     class Success<T>(data: T? = null) : APIState<T>(data = data)
     class Failure<T>( errorMessage: String) : APIState<T>(errorMessage = errorMessage)
     class Empty<T>: APIState<T>()
}



// Old  : Unable to handle Empty {Best for live Data}
//sealed class NetworkResult1<T> {
//    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
//    data class Success<T>(val data: T) : NetworkResult<T>()
//    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
//}
