package com.example.urbandictionaryapp.presentation.utils

import com.example.urbandictionaryapp.core.Session
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

//Checks internet connection
fun isConnectedToInternet(): Boolean {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val exitValue = ipProcess.waitFor()
        val response = (exitValue == 0)
        Timber.d("_TAG: isConnectedToInternet: connected: $response")
        Session.currentViewModel?.noInternet = !response
        return response
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}

//Not needed but kept for scalability purposes
@Suppress("UNCHECKED_CAST")
fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = instance::class.memberProperties
        // don't cast here to <Any, R>, it would succeed silently
        .first { it.name == propertyName } as KProperty1<Any, *>
    // force a invalid cast exception if incorrect type here
    return property.get(instance) as R
}

//waits n seconds and notifies when its done
fun wait(seconds: Int, afterDone: () -> Unit) = GlobalScope.launch {
    delay((seconds * 1000).toLong())

    withContext(Dispatchers.Main) {
        afterDone()
    }
}