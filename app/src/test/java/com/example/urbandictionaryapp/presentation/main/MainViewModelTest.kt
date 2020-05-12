package com.example.urbandictionaryapp.presentation.main

import android.content.Context
import android.os.Build
import androidx.databinding.library.baseAdapters.BR
import androidx.test.platform.app.InstrumentationRegistry
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.core.dateFormat
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.readJsonResponseFromResource
import com.example.urbandictionaryapp.repository.remote.ServerRepository
import com.example.urbandictionaryapp.repository.remote.request.DefineResponse
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainViewModelTest {
    //variables
    private lateinit var viewModel: MainViewModel
    private val repository = mock(ServerRepository::class.java)
    private lateinit var context: Context

    @Before
    fun setUp() {
        //creates viewModel for all the tests
        viewModel = MainViewModel(repository)
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @After
    fun wrappingThingsUp() {
        //needs to stop koin after each test
        stopKoin()
    }

    //reads the response (mock) from resources
    private fun setAvailableDefinitions() {
        val jsonString = context.resources.openRawResource(R.raw.get_definitions)
            .bufferedReader().use { it.readText() }
        val remoteResponse =
            readJsonResponseFromResource(false, DefineResponse::class.java, jsonString)
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
    fun testTerm() {
        //tests that a term is being written
        val testString = "wat"
        viewModel.onPropertyChanged(BR.term) {
            assertTrue(viewModel.term == testString)
        }
        viewModel.term = testString
    }

    @Test
    fun testAvailableDefinitions() {
        //test that the response is being kept
        viewModel.onPropertyChanged(BR.availableDefinitions) {
            assertTrue(viewModel.availableDefinitions.size == 10)
        }
        setAvailableDefinitions()
    }

    @Test
    fun testRecyclerViewItemViewModels() {
        //test the recycler items being assigned
        viewModel.onPropertyChanged(BR.availableDefinitions) {
            assertTrue(viewModel.recyclerViewItemViewModels.size == 10)
        }
        setAvailableDefinitions()
    }

    @Test
    fun testSorted() {
        //test the sort being done
        assertFalse(viewModel.sorted)
        setAvailableDefinitions()

        // Pending to implement assert on sorting
        viewModel.onPropertyChanged(BR.sorted) {
            assertTrue(viewModel.sorted)
        }

        viewModel.sortThumbsUp()
        viewModel.sortThumbsDown()
    }
}