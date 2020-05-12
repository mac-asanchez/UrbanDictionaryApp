package com.example.urbandictionaryapp.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.urbandictionaryapp.presentation.base.BaseViewModel

//Storing session information - not needed for the scope of this project
//Kept for scalability purposes
//If you have a userId this is where you keep it for the session
object Session {
    var currentFragment: Fragment? = null
    var currentActivity: AppCompatActivity? = null
    var currentViewModel: BaseViewModel? = null
}