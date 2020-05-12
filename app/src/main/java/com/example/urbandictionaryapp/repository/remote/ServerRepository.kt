package com.example.urbandictionaryapp.repository.remote

import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.repository.ApiResult
import kotlinx.coroutines.Deferred

interface ServerRepository {
    fun getDefinitionsAsync(term: String): Deferred<ApiResult<List<Definition>>>
}