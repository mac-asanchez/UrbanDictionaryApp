package com.example.urbandictionaryapp.presentation.repository.remote

import com.example.urbandictionaryapp.BuildConfig
import com.example.urbandictionaryapp.presentation.repository.remote.request.DefineResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ServerAPI {
    @GET(BuildConfig.GET_DEFINE_PATH)
    fun getDefinitionsAsync(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") key: String,
        @Query(value = "term") term: String
    ): Deferred<DefineResponse>
}