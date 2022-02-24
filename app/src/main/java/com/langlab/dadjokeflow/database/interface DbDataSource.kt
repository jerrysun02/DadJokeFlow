package com.langlab.dadjokeflow.database

import com.langlab.dadjokeflow.model.Joke

interface DbDataSource {
    suspend fun getJokes(): List<Joke>
    suspend fun addJokes(jokes: List<JokeDTO>)
}