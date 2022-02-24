package com.langlab.dadjokeflow.database

import android.content.Context
import com.langlab.dadjokeflow.model.Joke
import javax.inject.Inject

class DbDataSourceImpl @Inject constructor(context: Context): DbDataSource {
    private lateinit var jokeDao:JokeDao

    init {
        val db = JokeDatabase.getInstance(context)
        db?.let {
            jokeDao = it.jokeDao()
        }
    }

    override suspend fun getJokes(): List<Joke> {
        return jokeDao.jokes().map {
            Joke(it.title, it.content)
        }
    }

    override suspend fun addJokes(jokes: List<JokeDTO>) {
        jokeDao.add(jokes)
    }
}