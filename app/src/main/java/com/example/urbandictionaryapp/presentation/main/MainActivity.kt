package com.example.urbandictionaryapp.presentation.main

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.databinding.MainActivityLayoutBinding
import com.example.urbandictionaryapp.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var layout: MainActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wireOnPropertyChanged()
    }

    private fun wireOnPropertyChanged() {
        Timber.d("MainActivity_TAG: wireOnPropertyChanged: ")
        viewModel.onPropertyChanged(BR.term) {
            Timber.d("MainActivity_TAG: wireOnPropertyChanged: term: ${viewModel.term}")
            viewModel.getDefinitions()
        }

        viewModel.onPropertyChanged(BR.availableDefinitions) {
            Timber.d("MainActivity_TAG: wireOnPropertyChanged: availableDefinitions: ${viewModel.availableDefinitions.size}")
        }
    }

    override fun onResume() {
        super.onResume()
        layout =
            setBinding(this, R.layout.main_activity_layout, viewModel) as MainActivityLayoutBinding

        viewModel.term = "wat"
    }
}
