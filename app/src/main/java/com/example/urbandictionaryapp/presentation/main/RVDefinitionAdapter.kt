package com.example.urbandictionaryapp.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.urbandictionaryapp.databinding.DefinitionItemLayoutBinding
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.base.BaseRVAdapter

class RVDefinitionAdapter(
    listener: (View, Definition) -> Unit
) : BaseRVAdapter<Definition, DefinitionItemViewModel, DefinitionItemLayoutBinding>(listener) {
    override fun inflateDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DefinitionItemLayoutBinding = DefinitionItemLayoutBinding.inflate(inflater, container, false)

    override fun getBindItem(itemViewModel: DefinitionItemViewModel): Definition? =
        itemViewModel.definition
}