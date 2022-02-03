package com.langlab.dadjokeflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.langlab.dadjokeflow.model.Joke
import com.langlab.dadjokeflow.repository.DadJokeRepository
import com.langlab.dadjokeflow.repository.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DadJokeViewModel @Inject constructor(private val repository: DadJokeRepository) : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>().apply { value = emptyList()}
    val jokes: LiveData<List<Joke>> = _jokes

    fun fetchJokes() {
        viewModelScope.launch {
            var result: RemoteResult<Joke> = withContext(Dispatchers.IO) {
                repository.retrieveJokes()
            }

            if (result is RemoteResult.Success) {
                _jokes.value = listOf(result.data)
            }
        }
    }
}