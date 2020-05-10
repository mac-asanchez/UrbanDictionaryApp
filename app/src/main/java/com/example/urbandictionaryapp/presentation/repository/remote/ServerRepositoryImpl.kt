package com.example.urbandictionaryapp.presentation.repository.remote

import com.example.urbandictionaryapp.BuildConfig
import com.example.urbandictionaryapp.core.dateFormat
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.repository.ApiResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber

class ServerRepositoryImpl(
    private val serverAPI: ServerAPI
) : ServerRepository {
    private val cachedDefinitions = mutableMapOf<String, List<Definition>>()

    override fun getDefinitionsAsync(term: String): Deferred<ApiResult<List<Definition>>> =
        GlobalScope.async {
            try {
                ApiResult.Ok(
                    cachedDefinitions[term] ?: serverAPI.getDefinitionsAsync(
                        host = BuildConfig.HOST,
                        key = BuildConfig.KEY,
                        term = term
                    ).await().list.map {
                        Definition(
                            definition = it.definition,
                            permalink = it.permalink,
                            thumbsUp = it.thumbs_up,
                            soundUrls = it.sound_urls,
                            author = it.author,
                            word = it.word,
                            defId = it.defid,
                            currentVote = it.current_vote,
                            writtenOn = dateFormat.parse(
                                it.written_on.replace("T", " ").replace("Z", "")
                            ),
                            example = it.example,
                            thumbsDown = it.thumbs_down
                        )
                    }.run {
                        cachedDefinitions[term] = this
                        this
                    }
                )
            } catch (e: Exception) {
                Timber.d("ServerRepositoryImpl_TAG: getDefinitions: exception: $e")
                ApiResult.Error(e)
            }
        }
}