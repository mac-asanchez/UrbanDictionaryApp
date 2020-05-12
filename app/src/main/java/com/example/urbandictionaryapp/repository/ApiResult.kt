package com.example.urbandictionaryapp.repository

//Defining the types of api results you expect
//You can have multiple types results for better handling the api calls
sealed class ApiResult<out R> {
    class Error(val error: Exception) : ApiResult<Nothing>()
    class Ok<out R>(val result: R) : ApiResult<R>()
}