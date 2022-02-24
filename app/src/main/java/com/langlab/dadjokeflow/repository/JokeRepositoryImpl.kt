package com.langlab.dadjokeflow.repository

import android.content.Context
import com.langlab.dadjokeflow.database.DbDataSourceImpl
import com.langlab.dadjokeflow.database.JokeDTO
import com.langlab.dadjokeflow.model.Joke
import com.langlab.dadjokeflow.remote.RemoteDataSourceImpl
import com.langlab.dadjokeflow.remote.RemoteResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl
): JokeRepository {

    override suspend fun getRemoteJoke(): RemoteResult<Joke> {
        return remoteDataSource.fetchJokes()
    }

    override suspend fun addLocalJoke(jokes: List<Joke>, context: Context) {
        val dbDataSource = DbDataSourceImpl(context)
        dbDataSource.addJokes(
            jokes.map {
                JokeDTO(it.title!!, it.content)
            }
        )
    }

    override suspend fun readLocalJokes(context: Context): List<Joke> {
        val dbDataSource = DbDataSourceImpl(context)
        return dbDataSource.getJokes()
    }

    val newJokes: Flow<RemoteResult<Joke>> = flow {
        var i = 0
        while(i < 5) {
            emit(remoteDataSource.fetchJokes())
            delay(20 * 1000)
            i++
        }
    }
}