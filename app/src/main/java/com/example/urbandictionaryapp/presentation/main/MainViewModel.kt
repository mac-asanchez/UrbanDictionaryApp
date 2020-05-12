package com.example.urbandictionaryapp.presentation.main

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.base.BaseViewModel
import com.example.urbandictionaryapp.repository.ApiResult
import com.example.urbandictionaryapp.repository.remote.ServerRepository
import com.example.urbandictionaryapp.repository.runOnResult
import timber.log.Timber

class MainViewModel(
    /*repository injected*/
    private val repository: ServerRepository
) : BaseViewModel() {
    //bindable property that changes from the UI
    @Bindable
    var term: String = ""
        set(value) {
            field = value
            //Communication to the Activity
            notifyPropertyChanged(BR.term)
        }

    @get:Bindable
    var availableDefinitions = emptyList<Definition>()
        set(value) {
            field = value
            //fills the recyclerview items
            recyclerViewItemViewModels = value.map {
                DefinitionItemViewModel().apply {
                    definition = it
                }
            }.toMutableList()
            notifyPropertyChanged(BR.availableDefinitions)
        }

    var recyclerViewItemViewModels = mutableListOf<DefinitionItemViewModel>()
        private set

    var exitEnabled = false

    @get:Bindable
    var sorted = false
        set(value) {
            field = value
            if (value)
                notifyPropertyChanged(BR.sorted)
        }

    //region functions
    fun getDefinitions() = background {
        Timber.d("MainViewModel_TAG: getDefinition: ")
        repository.getDefinitionsAsync(term).runOnResult {
            when (this) {
                is ApiResult.Error -> Timber.d("MainViewModel_TAG: getDefinition: error: $error")
                is ApiResult.Ok -> availableDefinitions = result
            }
        }
    }

    fun sortThumbsUp() {
        Timber.d("MainViewModel_TAG: sortThumbsUp: ")
        loading = true
        recyclerViewItemViewModels.sortByDescending { it.definition?.thumbsUp }
        sorted = true
    }

    fun sortThumbsDown() {
        Timber.d("MainViewModel_TAG: sortThumbsDown: ")
        loading = true
        recyclerViewItemViewModels.sortByDescending { it.definition?.thumbsDown }
        sorted = true
    }
    //endregion
}