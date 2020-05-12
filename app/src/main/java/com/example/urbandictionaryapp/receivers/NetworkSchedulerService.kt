package com.example.urbandictionaryapp.receivers

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.IntentFilter
import com.example.urbandictionaryapp.core.CONNECTIVITY_ACTION
import com.example.urbandictionaryapp.core.Session

import timber.log.Timber

//Network Scheduler Service
class NetworkSchedulerService() : JobService(), ConnectivityReceiver.ConnectivityReceiverListener {
    private val mConnectivityReceiver: ConnectivityReceiver = ConnectivityReceiver(this)

    override fun onCreate() {
        super.onCreate()
        Timber.d("NetworkSchedulerService_TAG: onCreate: service created")
    }

    /**
     * When the app's NetworkConnectionActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("NetworkSchedulerService_TAG: onStartCommand: ")
        return Service.START_NOT_STICKY
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Timber.d("NetworkSchedulerService_TAG: onStopJob: ")
        unregisterReceiver(mConnectivityReceiver)
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Timber.d("NetworkSchedulerService_TAG: onStartJob: $mConnectivityReceiver")
        registerReceiver(mConnectivityReceiver, IntentFilter(CONNECTIVITY_ACTION))
        return true
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        Timber.d("NetworkSchedulerService_TAG: onNetworkConnectionChanged: isConnected: $isConnected")
        Session.currentViewModel?.noInternet = !isConnected
    }
}
