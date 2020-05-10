package com.example.urbandictionaryapp.presentation.main

import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.base.BaseViewModel

class DefinitionItemViewModel : BaseViewModel() {
    var definition: Definition? = null

    val title: String?
        get() = definition?.word

    val text: String?
        get() = definition?.definition

    val author: String?
        get() = definition?.author

    val thumbsUp: String?
        get() = definition?.thumbsUp?.toString() ?: "0"

    val thumbsDown: String?
        get() = definition?.thumbsDown?.toString() ?: "0"
}