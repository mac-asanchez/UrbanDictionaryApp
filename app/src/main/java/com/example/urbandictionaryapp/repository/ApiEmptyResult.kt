package com.example.urbandictionaryapp.repository

//Not needed but kept for scalability purposes
sealed class ApiEmptyResult {
    class Err(val error: Exception) : ApiEmptyResult()
    object Ok : ApiEmptyResult()
}