package com.example.urbandictionaryapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Definition(
    val definition: String,
    val permalink: String,
    val thumbsUp: Int,
    val soundUrls: List<String>,
    val author: String,
    val word: String,
    val defId: Int,
    val currentVote: String,
    val writtenOn: Date?,
    val example: String,
    val thumbsDown: Int,
    var currentSoundIndex: Int = 0
) : Parcelable