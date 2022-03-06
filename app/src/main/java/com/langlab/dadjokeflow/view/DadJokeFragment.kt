package com.langlab.dadjokeflow.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.langlab.dadjokeflow.R
import com.langlab.dadjokeflow.databinding.DadJokeFragmentBinding
import com.langlab.dadjokeflow.model.Joke
import com.langlab.dadjokeflow.viewmodel.DadJokeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DadJokeFragment : Fragment(R.layout.dad_joke_fragment) {
    private lateinit var binding: DadJokeFragmentBinding
    private lateinit var adapter: JokeListAdapter
    private val viewModel: DadJokeViewModel by viewModels()
    private var jokeList = mutableListOf<Joke>()

    companion object {
        fun newInstance() = DadJokeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DadJokeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupViewModel()
        //setupUI()
        setupJokeFlow()
    }

    override fun onResume() {
        super.onResume()
        //viewModel.fetchJokes()
    }

    private val showJokes = Observer<List<Joke>> {
        adapter.update(it)
    }

    private fun setupViewModel() {
        viewModel.jokes.observe(viewLifecycleOwner, showJokes)
    }

    private fun setupUI() {
        adapter = JokeListAdapter(viewModel.jokes.value ?: emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun setupJokeFlow() {
        adapter = JokeListAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.fetchJokeFlow()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jokesFlow.collect {
                    if (it.isNotEmpty()) {
                        for (joke: Joke in it)
                            jokeList.add(joke)
                        adapter.update(jokeList.asReversed())
                    }
                }
            }
        }
    }
}