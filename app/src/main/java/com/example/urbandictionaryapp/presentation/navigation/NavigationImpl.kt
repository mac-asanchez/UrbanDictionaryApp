package com.example.urbandictionaryapp.presentation.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.urbandictionaryapp.core.Session
import com.example.urbandictionaryapp.presentation.main.MainActivity
import timber.log.Timber

class NavigationImpl : Navigation {
    override fun navigateBack(fromActivity: AppCompatActivity, defaultBehavior: () -> Unit) {
        Timber.d("NavigationImpl_TAG: navigateBack: from: ${fromActivity::class.simpleName}")

        //region validate No Return Activity
        val noReturn = noReturnActivity.find { fromActivity::class.java == it }
        if (noReturn != null) return
        //endregion

        //region get Previous Activity or default behavior
        val toActivity = getPreviousScreen(fromActivity)
        if (toActivity == null) {
            defaultBehavior()
            return
        }
        //endregion

        navigate(fromActivity, toActivity)
    }

    override fun navigateHome(fromActivity: AppCompatActivity, extras: Bundle?) {
        Timber.d("NavigationImpl_TAG: navigateHome: from: ${fromActivity::class.simpleName}, extras: $extras")
        if (Session.currentActivity != null && Session.currentActivity!!::class.java == MainActivity::class.java) return
        navigate(fromActivity, MainActivity::class.java)
    }

    override fun onDone(fromActivity: AppCompatActivity, extras: Bundle?) {
        Timber.d("NavigationImpl_TAG: onDone: from: ${fromActivity::class.simpleName}, extras: $extras")
        val toActivity = getNextScreen(fromActivity) ?: return

        navigate(fromActivity, toActivity, extras)
    }

    override fun navigateToDetails(fromActivity: AppCompatActivity, extras: Bundle?) {
        Timber.d("NavigationImpl_TAG: navigateToDetails: from: ${fromActivity::class.java}, extras: $extras")
        val toActivity = getDetailsScreen(fromActivity) ?: return

        navigate(fromActivity, toActivity, extras)
    }

    private fun <T> navigate(
        context: Context,
        view: Class<T>,
        extras: Bundle? = null
    ) {
        Timber.d("NavigationImpl_TAG: navigate: ")
        val intent = Intent(context, view)
        if (extras != null) {
            intent.putExtras(extras)
        }

        context.startActivity(intent)
    }

    private fun getPreviousScreen(fromActivity: AppCompatActivity): Class<*>? =
        when (fromActivity::class) {
            MainActivity::class -> MainActivity::class.java
            else -> null
        }

    private fun getNextScreen(fromActivity: AppCompatActivity): Class<*>? =
        when (fromActivity::class) {
//            MainActivity::class -> MainActivity::class.java
            else -> null
        }

    private fun getDetailsScreen(fromActivity: AppCompatActivity): Class<*>? =
        when (fromActivity::class) {
            MainActivity::class -> MainActivity::class.java
            else -> null
        }

    override fun navigateToNoInternet(fromActivity: AppCompatActivity) {
        Timber.d("NavigationImpl_TAG: navigateToNetworkType: ")
        val toActivity = when (fromActivity::class) {
            else -> MainActivity::class.java
        }
        navigate(fromActivity, toActivity, null)
    }

    private val noReturnActivity = listOf<Class<*>>(
        MainActivity::class.java
    )
}