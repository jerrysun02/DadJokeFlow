package com.langlab.dadjokeflow.data

import com.langlab.dadjokeflow.model.Joke
import com.langlab.dadjokeflow.remote.RemoteResult

interface JokeDataSource {
    suspend fun fetchJokes(): RemoteResult<Joke>
    suspend fun saveJokes(joke: List<Joke>)
    suspend fun readJokes(): List<Joke>
}