package com.example.urbandictionaryapp.presentation.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

interface Navigation {
    fun navigateBack(fromActivity: AppCompatActivity, defaultBehavior: () -> Unit)
    fun navigateHome(fromActivity: AppCompatActivity, extras: Bundle? = null)
    fun onDone(fromActivity: AppCompatActivity, extras: Bundle? = null)
    fun navigateToDetails(fromActivity: AppCompatActivity, extras: Bundle? = null)
    fun navigateToNoInternet(fromActivity: AppCompatActivity)
}