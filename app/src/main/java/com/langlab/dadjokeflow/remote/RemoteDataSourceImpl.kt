package com.langlab.dadjokeflow.remote

import com.langlab.dadjokeflow.model.Joke
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {
    override suspend fun fetchJokes(): RemoteResult<Joke> {
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