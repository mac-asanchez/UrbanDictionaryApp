package com.example.urbandictionaryapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

fun <T> readJsonResponseFromResource(list: Boolean = false, myType: Class<T>, jsonString: String): T? {
    val moshi = Moshi.Builder().build()
    val adapter = if (!list) {
        moshi.adapter(myType)
    } else {
        val type = Types.newParameterizedType(List::class.java, myType)
        moshi.adapter(type)
    }

    return adapter.fromJson(jsonString)
}