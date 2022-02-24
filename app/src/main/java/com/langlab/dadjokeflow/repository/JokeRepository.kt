package com.langlab.dadjokeflow.repository

import android.content.Context
import com.langlab.dadjokeflow.database.JokeDTO
import com.langlab.dadjokeflow.model.Joke
import com.langlab.dadjokeflow.remote.RemoteResult

interface JokeRepository {
    suspend fun getRemoteJoke(): RemoteResult<Joke>
    suspend fun addLocalJoke(joke: List<Joke>, context: Context)
    suspend fun readLocalJokes(context: Context): List<Joke>
}