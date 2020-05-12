package com.example.urbandictionaryapp.repository

sealed class ApiEmptyResult {
    class Err(val error: Exception) : ApiEmptyResult()
    object Ok : ApiEmptyResult()
}