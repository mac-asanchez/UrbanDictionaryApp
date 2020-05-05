package com.example.urbandictionaryapp.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment

interface BaseFragmentListener {
    fun onClicked(fromFragment: Fragment, extras: Bundle? = null)
}