package com.example.urbandictionaryapp.presentation.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import com.example.urbandictionaryapp.BR
import com.example.urbandictionaryapp.DelayableIdlingResource
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.ScreenUnitTestRule
import com.example.urbandictionaryapp.core.dateFormat
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.readJsonResponse
import com.example.urbandictionaryapp.repository.remote.ServerRepository
import com.example.urbandictionaryapp.repository.remote.request.DefineResponse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@LargeTest
@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {
    private lateinit var viewModel: MainViewModel
    private val repository = Mockito.mock(ServerRepository::class.java)
    val waitIdlingResource = DelayableIdlingResource()

    @get:Rule
    val activityTestRule = ScreenUnitTestRule(
        activityClass = MainActivity::class.java,
        navigateToScreen = {},
        setupMocks = {
            viewModel = MainViewModel(repository)
        }
    )

    private fun setUp() {
        (activityTestRule.activity as MainActivity).run {
            layout.setVariable(BR.viewModel, viewModel)
            layout.executePendingBindings()
        }
    }

    private val etTerm = withId(R.id.etTerm)

    @Test
    fun writeTerm() {
        onView(etTerm).perform(typeText("wat"))
        onView(etTerm).check(matches(withText("wat")))
    }

    private fun setAvailableDefinitions() {
        val jsonString = activityTestRule.activity.resources.openRawResource(R.raw.get_definitions)
            .bufferedReader().use { it.readText() }
        val remoteResponse = readJsonResponse(false, DefineResponse::class.java, jsonString)
        viewModel.availableDefinitions = remoteResponse!!.list.map {
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
        }
    }

    @Test
    fun loadRecyclerView() {
        waitIdlingResource.wait(5000)
        setUp()
        viewModel.onPropertyChanged(BR.availableDefinitions) {
            assertTrue(viewModel.availableDefinitions.size == 10)
        }
        setAvailableDefinitions()
    }
}