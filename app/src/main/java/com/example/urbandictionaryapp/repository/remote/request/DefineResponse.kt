package com.example.urbandictionaryapp.repository.remote.request


import com.squareup.moshi.Json

data class DefineResponse(
    @Json(name = "list")
    val list: List<DefinitionResponse>
) {
    data class DefinitionResponse(
        @Json(name = "author") val author: String,
        @Json(name = "current_vote") val current_vote: String,
        @Json(name = "defid") val defid: Int,
        @Json(name = "definition") val definition: String,
        @Json(name = "example") val example: String,
        @Json(name = "permalink") val permalink: String,
        @Json(name = "sound_urls") val sound_urls: List<String>,
        @Json(name = "thumbs_down") val thumbs_down: Int,
        @Json(name = "thumbs_up") val thumbs_up: Int,
        @Json(name = "word") val word: String,
        @Json(name = "written_on") val written_on: String
    )
}