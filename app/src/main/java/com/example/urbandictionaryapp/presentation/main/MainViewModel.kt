package com.example.urbandictionaryapp.presentation.main

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.base.BaseViewModel
import com.example.urbandictionaryapp.presentation.repository.ApiResult
import com.example.urbandictionaryapp.presentation.repository.remote.ServerRepository
import com.example.urbandictionaryapp.presentation.repository.runOnResult
import timber.log.Timber

class MainViewModel(
    private val repository: ServerRepository
) : BaseViewModel() {
    @Bindable
    var term: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.term)
        }

    @get:Bindable
    var availableDefinitions = emptyList<Definition>()
    set(value) {
        field = value
        notifyPropertyChanged(BR.availableDefinitions)
    }

    fun getDefinitions() = background {
        Timber.d("MainViewModel_TAG: getDefinition: ")
        repository.getDefinitionsAsync(term).runOnResult {
            when (this) {
                is ApiResult.Error -> Timber.d("MainViewModel_TAG: getDefinition: error: $error")
                is ApiResult.Ok -> availableDefinitions = result
            }
        }
    }
}