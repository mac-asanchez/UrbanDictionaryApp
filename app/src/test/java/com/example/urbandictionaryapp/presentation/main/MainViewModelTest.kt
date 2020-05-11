package com.example.urbandictionaryapp.presentation.main

import android.content.Context
import android.os.Build
import androidx.databinding.library.baseAdapters.BR
import androidx.test.platform.app.InstrumentationRegistry
import com.example.urbandictionaryapp.core.dateFormat
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.repository.remote.ServerRepository
import com.example.urbandictionaryapp.presentation.repository.remote.request.DefineResponse
import com.example.urbandictionaryapp.readJsonResponse
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val repository = mock(ServerRepository::class.java)
    private lateinit var context: Context

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
        context = InstrumentationRegistry.getInstrumentation().context
    }

    private fun setAvailableDefinitions() {
        val filePath =
            "/Users/arturosanchez/Desktop/Project/Challenges/UrbanDictionaryApp/app/src/test/java/com/example/urbandictionaryapp/repository/get_definitions.json"
        val remoteResponse = readJsonResponse(false, DefineResponse::class.java, filePath)
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
        val testString = "wat"
        viewModel.onPropertyChanged(BR.term) {
            assertTrue(viewModel.term == testString)
        }
        viewModel.term = testString
    }

    @Test
    fun testAvailableDefinitions() {
        viewModel.onPropertyChanged(BR.availableDefinitions) {
            assertTrue(viewModel.availableDefinitions.size == 10)
        }
        setAvailableDefinitions()
    }

    @Test
    fun testRecyclerViewItemViewModels() {
        viewModel.onPropertyChanged(BR.availableDefinitions) {
            assertTrue(viewModel.recyclerViewItemViewModels.size == 10)
        }
        setAvailableDefinitions()
    }

    @Test
    fun testSorted() {
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