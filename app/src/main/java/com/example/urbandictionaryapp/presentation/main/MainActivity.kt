package com.example.urbandictionaryapp.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.databinding.MainActivityLayoutBinding
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.presentation.base.BaseActivity
import com.example.urbandictionaryapp.presentation.utils.wait
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var layout: MainActivityLayoutBinding
    private lateinit var definitionsAdapter: RVDefinitionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wireOnPropertyChanged()
    }

    private fun wireOnPropertyChanged() {
        Timber.d("MainActivity_TAG: wireOnPropertyChanged: ")
        viewModel.onPropertyChanged(BR.term) {
            Timber.d("MainActivity_TAG: wireOnPropertyChanged: term: ${viewModel.term}")
            viewModel.getDefinitions()
        }

        viewModel.onPropertyChanged(BR.availableDefinitions) {
            Timber.d("MainActivity_TAG: wireOnPropertyChanged: availableDefinitions: ${viewModel.availableDefinitions.size}")
            definitionsAdapter.itemList = viewModel.recyclerViewItemViewModels
            viewModel.loading = false
        }

        viewModel.onPropertyChanged(BR.sorted) {
            Timber.d("MainActivity_TAG: wireOnPropertyChanged: sorted")
            definitionsAdapter.itemList = viewModel.recyclerViewItemViewModels
            viewModel.loading = false
        }
    }

    private fun initRecyclerView() {
        Timber.d("MainActivity_TAG: initRecyclerView: ")
        definitionsAdapter = RVDefinitionAdapter { _, definition -> playSound(definition) }
        layout.rvDefinitions.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = definitionsAdapter
        }
        definitionsAdapter.itemList = viewModel.recyclerViewItemViewModels
    }

    private fun playSound(definition: Definition) {
        Timber.d("MainActivity_TAG: playSound: ${definition.word}, currentSoundIndex: ${definition.currentSoundIndex}")
    }

    override fun onResume() {
        super.onResume()
        layout =
            setBinding(this, R.layout.main_activity_layout, viewModel) as MainActivityLayoutBinding
        initRecyclerView()
        viewModel.loading = false
    }

    override fun onBackPressed() {
        when (viewModel.exitEnabled) {
            true -> super.onBackPressed()
            else -> {
                viewModel.exitEnabled = true
                Toast.makeText(this, getString(R.string.exit_message), Toast.LENGTH_SHORT).show()
                wait(3) {
                    viewModel.exitEnabled = false
                }
            }
        }
    }
}
