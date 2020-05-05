package com.example.urbandictionaryapp.presentation.utils

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.urbandictionaryapp.core.Session
import com.example.urbandictionaryapp.receivers.NetworkSchedulerService
import timber.log.Timber
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

inline fun View.waitForLayout(crossinline f: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            try {
                removeOnGlobalLayoutListener(this)
                f()
            } catch (e: Exception) {

            }
        }
    })
}

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

fun AppCompatActivity.scheduleConnectivityJob() {
    Timber.d("_TAG: scheduleConnectivityJob: ")
    val connectivityJob =
        JobInfo.Builder(0, ComponentName(this, NetworkSchedulerService::class.java))
            .setRequiresCharging(true)
            .setMinimumLatency(1000)
            .setOverrideDeadline(2000)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .build()

    val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    jobScheduler.schedule(connectivityJob)
}

@Suppress("UNCHECKED_CAST")
fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = instance::class.memberProperties
        // don't cast here to <Any, R>, it would succeed silently
        .first { it.name == propertyName } as KProperty1<Any, *>
    // force a invalid cast exception if incorrect type here
    return property.get(instance) as R
}