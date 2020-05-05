package com.example.urbandictionaryapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.urbandictionaryapp.presentation.utils.isConnectedToInternet


class ConnectivityReceiver(
    private val mConnectivityReceiverListener: ConnectivityReceiverListener
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        mConnectivityReceiverListener.onNetworkConnectionChanged(isConnectedToInternet())
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}
