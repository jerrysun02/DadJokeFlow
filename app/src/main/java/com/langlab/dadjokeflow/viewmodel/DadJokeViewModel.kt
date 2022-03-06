package com.langlab.dadjokeflow.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.langlab.dadjokeflow.model.Joke
import com.langlab.dadjokeflow.remote.RemoteResult
import com.langlab.dadjokeflow.repository.JokeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DadJokeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    //private val repository: DadJokeRepository,
    private val newRepo: JokeRepositoryImpl
    ) : ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>().apply { value = emptyList()}
    val jokes: LiveData<List<Joke>> = _jokes

    fun fetchJokes() {
        viewModelScope.launch {
            var result: RemoteResult<Joke> = withContext(Dispatchers.IO) {
                newRepo.getRemoteJoke()
            }

            if (result is RemoteResult.Success) {
                _jokes.value = listOf(result.data)
            }
        }
    }

    /*fun fetchJokes() {
        viewModelScope.launch {
            var result: RemoteResult<Joke> = withContext(Dispatchers.IO) {
                repository.retrieveJokes()
            }

            if (result is RemoteResult.Success) {
                _jokes.value = listOf(result.data)
            }
        }
    }*/

    private val _jokesFlow = MutableStateFlow<List<Joke>>(emptyList())
    val jokesFlow: StateFlow<List<Joke>> = _jokesFlow

    fun fetchJokeFlow() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _jokesFlow.value = newRepo.readLocalJokes(context)
            }
            newRepo.newJokes
                .collect {
                    if (it is RemoteResult.Success) {
                        newRepo.addLocalJoke(listOf(it.data), context)
                        _jokesFlow.value = listOf(it.data)
                    }
                }
        }
    }

    /*fun fetchJokeFlow() {
        viewModelScope.launch {
            repository.latestJokes
                .collect {
                    _jokesFlow.value = it
                }
        }
    }*/
}