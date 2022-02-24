package com.langlab.dadjokeflow.remote

import com.langlab.dadjokeflow.model.Joke

interface RemoteDataSource {
    suspend fun fetchJokes(): RemoteResult<Joke>
}