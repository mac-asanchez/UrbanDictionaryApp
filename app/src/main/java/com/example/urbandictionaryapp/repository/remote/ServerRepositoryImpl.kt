package com.example.urbandictionaryapp.repository.remote

import com.example.urbandictionaryapp.BuildConfig
import com.example.urbandictionaryapp.core.dateFormat
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.repository.ApiResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber

//Server Calls implementation
class ServerRepositoryImpl(
    /*Retrofit interface gets injected*/
    private val serverAPI: ServerAPI
) : ServerRepository {
    //Cache variable
    private val cachedDefinitions = mutableMapOf<String, List<Definition>>()

    //Runs on a thread
    override fun getDefinitionsAsync(term: String): Deferred<ApiResult<List<Definition>>> =
        GlobalScope.async {
            try {
                //Expected Result is ApiResult.OK
                ApiResult.Ok(
                    //If there is no cache data for that term gets it from the server
                    cachedDefinitions[term] ?: serverAPI.getDefinitionsAsync(
                        host = BuildConfig.HOST,
                        key = BuildConfig.KEY,
                        term = term
                    ).await().list.map {
                        //Map the response to the desired model
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
                        //Once is done it saves the data in the cache
                        cachedDefinitions[term] = this
                        //returns the result
                        this
                    }
                )
            } catch (e: Exception) {
                //if it crashes the result is ApiResult.Error, this helps handling the problem
                //We can have multiple types of results
                Timber.d("ServerRepositoryImpl_TAG: getDefinitions: exception: $e")
                ApiResult.Error(e)
            }
        }
}