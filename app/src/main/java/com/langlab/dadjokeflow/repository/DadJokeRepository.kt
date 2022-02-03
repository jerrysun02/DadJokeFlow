package com.langlab.dadjokeflow.repository

import com.langlab.dadjokeflow.model.Joke
import javax.inject.Inject

class DadJokeRepository @Inject constructor() {
    suspend fun retrieveJokes(): RemoteResult<Joke> {
        try {
            val response = RemoteApi.build()?.fetchJoke()
            response?.let {
                val jokeResponse = it.body()
                val joke = Joke(jokeResponse?.id, jokeResponse?.joke)
                return RemoteResult.Success(joke)
            }?:run {
                return RemoteResult.Error(Exception("Occur an error"))
            }
        } catch (e:Exception) {
            return RemoteResult.Error(e)
        }
    }
}