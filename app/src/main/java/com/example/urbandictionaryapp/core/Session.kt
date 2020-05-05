package com.example.urbandictionaryapp.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.urbandictionaryapp.presentation.base.BaseViewModel

object Session {
    var userId: String = ""
    var columnsToDisplay: Int = 3

    var currentFragment: Fragment? = null
    var currentActivity: AppCompatActivity? = null
    var currentViewModel: BaseViewModel? = null
}