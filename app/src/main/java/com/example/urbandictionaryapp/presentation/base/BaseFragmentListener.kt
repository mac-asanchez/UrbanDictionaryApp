package com.example.urbandictionaryapp.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment

//Not needed but kept for scalability purposes
interface BaseFragmentListener {
    fun onClicked(fromFragment: Fragment, extras: Bundle? = null)
}