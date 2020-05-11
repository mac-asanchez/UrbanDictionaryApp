package com.example.urbandictionaryapp.presentation.main

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

fun isKeyboardShown(): Boolean {
    val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
        Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isAcceptingText
}

fun <T> readJsonResponse(list: Boolean = false, myType: Class<T>, jsonString: String): T? {
    val moshi = Moshi.Builder().build()
    val adapter = if (!list) {
        moshi.adapter(myType)
    } else {
        val type = Types.newParameterizedType(List::class.java, myType)
        moshi.adapter(type)
    }

    return adapter.fromJson(jsonString)
}