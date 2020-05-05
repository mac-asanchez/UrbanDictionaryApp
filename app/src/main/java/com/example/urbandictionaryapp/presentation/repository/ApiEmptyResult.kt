package com.example.urbandictionaryapp.presentation.repository

sealed class ApiEmptyResult {
    class Err(val error: Exception) : ApiEmptyResult()
    object Ok : ApiEmptyResult()
}